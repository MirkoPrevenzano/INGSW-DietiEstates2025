






METTI IN SECURITY

    import com.dietiEstates.backend.service.AuthenticationService;
    import com.dietiEstates.backend.service.CustomOAuth2UserService;
    import com.dietiEstates.backend.service.OAuthLoginSuccessHandler;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;

                 .oauth2Login(a -> a.defaultSuccessUrl("/hello",true)
                               //.userInfoEndpoint(b -> b.userService(customOAuth2UserService))
                                .successHandler(oAuthLoginSuccessHandler)); 



  METTI IN AuthService
  
    public Customer processOAuthPostLogin(String name, String surname, String password, String email, String providerName)
    {
        Optional<Customer> existUser = customerRepository.findByUsername(email);
         
        if (existUser.isPresent()) {
            log.error("Customer with this e-mail is already present!");
            throw new IllegalArgumentException("Customer with this e-mail is already present!");
        }

            log.info("sono in processOauthPostLOgin");
            Customer newUser = new Customer();
            newUser.setAuthWithExternalAPI(true);
            newUser.setUsername(email);
            newUser.setProvider(Provider.valueOf(providerName.toUpperCase()));
            newUser.setName(name);             
            newUser.setSurname(surname);      
            newUser.setPassword(password);       

            return customerRepository.save(newUser);
    }




METTI IN SERVICE 

    package com.dietiEstates.backend.service;
    
    import java.io.IOException;
    import java.util.HashMap;
    import java.util.Map;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.HttpStatusCode;
    import org.springframework.http.MediaType;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
    import org.springframework.stereotype.Component;
    
    import com.dietiEstates.backend.config.security.JWTUtils;
    import com.dietiEstates.backend.model.CustomOAuth2User;
    import com.dietiEstates.backend.model.Customer;
    import com.fasterxml.jackson.databind.ObjectMapper;
    
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.extern.slf4j.Slf4j;
    
    @Component
    @Slf4j
    public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
     
        @Autowired AuthenticationService authenticationService;
         
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws ServletException, IOException 
        {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
    
            Customer customer;
            String client = oAuth2User.getOauth2ClientName();
            String fullName = oAuth2User.getAttribute("name");
            String email = oAuth2User.getAttribute("email");
            String name = "";
            String surname = "";
            String password = "default";
    
            System.out.println("Name : " + oAuth2User.getAttribute("name"));
            System.out.println("Email : " + oAuth2User.getAttribute("email"));
            System.out.println("Client : " + oAuth2User.getOauth2ClientName());
            //System.out.println(oAuth2User.getAttributes());
            
            if(client.equals("GitHub"))
            {
                if(email != null && fullName != null)
                {
                    String[] fullNameSplit = fullName.split(" ");
    
                    if(fullNameSplit.length > 1)
                    {
                        name = fullNameSplit[0];
                        surname = fullNameSplit[1];
                    }
                    else
                    {
                        name = fullName;
                        surname = "default";
                    }
                }
                else
                {
    
                }
            }
    
            try 
            {
                customer = authenticationService.processOAuthPostLogin(name, surname, password, email, client);
            } catch (Exception e) 
            {        
    /*             response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), "sdad"); */
                response.sendError(400, "lol");
            }
    
    
            String accessToken = JWTUtils.generateAccessToken(oAuth2User);
            log.info(accessToken);
            //super.onAuthenticationSuccess(request, response, authentication);
    
            // restituire oggetto json nel body
            Map<String,String> token = new HashMap<>(); 
            token.put("accessToken", accessToken);
    
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), token);
        }
     
    }



METTI IN FILTRI
    
    package com.dietiEstates.backend.config.security.filter;
    
    import static java.util.Arrays.stream;
    
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.Collection;
    
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.lang.NonNull;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
    import org.springframework.web.filter.OncePerRequestFilter;
    
    import com.auth0.jwt.exceptions.JWTVerificationException;
    import com.auth0.jwt.interfaces.DecodedJWT;
    
    import com.dietiEstates.backend.config.security.JWTUtils;
    
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    
    import lombok.extern.slf4j.Slf4j;
    
    
    @Slf4j
    public class JWTAuthorizationFilter extends OncePerRequestFilter
    {
        @Override
        protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                        @NonNull HttpServletResponse response, 
                                        @NonNull FilterChain filterChain) throws ServletException, IOException 
        {
            log.info("servletPath prima dell'if :{}", request.getServletPath());
    
            if(request.getServletPath().equals("/login") ||
               request.getServletPath().equals("/mirkos") || request.getServletPath().equals("/favicon.ico") || 
               request.getServletPath().equals("/default-ui.css"))
            {
                log.info("servletPath dell'if :{}", request.getServletPath());
                log.info("sono quiiii");
                filterChain.doFilter(request, response);
                log.info("sono quaaaa");
                //return;
            }
            else 
            {
                log.info("sono nell'else del JWT authoriz...");
                log.info("servletPath dopo dell'if :{}", request.getServletPath());
                String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    
                if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                {
                    try 
                    {
                        String token = authorizationHeader.substring("Bearer ".length());
                        DecodedJWT decodedJWT = JWTUtils.verifyToken(token);  
        
                        String username = decodedJWT.getSubject();
                        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        stream(roles).forEach(role -> {authorities.add(new SimpleGrantedAuthority(role));});
                            
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);   
                        log.info("JWT Authorization is OK!");
        
                        request.setAttribute("com.dietiEstates.backend.model.User.username", username);
                        filterChain.doFilter(request, response);  
                    } 
                    catch (UsernameNotFoundException e)
                    {
                        log.error("{}", e.getMessage());
                        response.setHeader("Error", e.getMessage());
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }
                    catch (JWTVerificationException e) 
                    {
                        log.error("{}", e.getMessage());
                        response.setHeader("Error", e.getMessage());
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }
                }
                else
                {
                    log.error("You are not a JWT Bearer!");
                    response.setHeader("Error", "You are not a JWT Bearer!");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
            }
        }
    }






METTI IN MODEL

    
    package com.dietiEstates.backend.model;
    
    import java.util.Collection;
    import java.util.List;
    import java.util.Map;
     
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.oauth2.core.user.OAuth2User;
    
    import lombok.extern.slf4j.Slf4j;
    
    
    @Slf4j
    public class CustomOAuth2User implements OAuth2User 
    {
        private OAuth2User oauth2User;
        private String oauth2ClientName;
        private Role role;
    
    
        public CustomOAuth2User(OAuth2User oauth2User, String oauth2ClientName) 
        {
            this.oauth2User = oauth2User;
            this.oauth2ClientName = oauth2ClientName;
            this.role = Role.ROLE_USER;
        }
     
    
    
        @Override
        public Map<String, Object> getAttributes() {
            return oauth2User.getAttributes();
        }
    
        @Override
        public Collection<SimpleGrantedAuthority> getAuthorities() 
        {
            return List.of(new SimpleGrantedAuthority(role.name()));
        }
    /*  
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return oauth2User.getAuthorities();
        }
      */
        @Override
        public String getName() {
            return oauth2User.getAttribute("name");
        }
     
        public String getEmail() {
            return oauth2User.getAttribute("email");     
        }
        
        public String getOauth2ClientName() {
            return this.oauth2ClientName;
        }
    }




METTI IN SERVICE
    
    
    package com.dietiEstates.backend.service;
    
    import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
    import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
    import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
    import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
    import org.springframework.security.oauth2.core.user.OAuth2User;
    import org.springframework.stereotype.Service;
    
    import com.dietiEstates.backend.model.CustomOAuth2User;
    
    import lombok.extern.slf4j.Slf4j;
     
    
    @Service
    @Slf4j
    public class CustomOAuth2UserService extends DefaultOAuth2UserService  {
     
        @Override
        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException 
        {    
            log.info("sono in loadUser");
    
            log.info("userRequest : {}", userRequest.toString());
    
            log.info("userRequest.AccessToken : {}", userRequest.getAccessToken().getTokenValue());
    
            log.info("userRequest.clientRegistration : {}", userRequest.getClientRegistration().toString());
    
            log.info("userRequest.clientRegistration.clientId : {}", userRequest.getClientRegistration().getClientId());
            log.info("userRequest.clientRegistration.getClientName : {}", userRequest.getClientRegistration().getClientName());
            log.info("userRequest.clientRegistration.getClientSecret : {}", userRequest.getClientRegistration().getClientSecret());
            log.info("userRequest.clientRegistration.getRedirectUri : {}", userRequest.getClientRegistration().getRedirectUri());
            log.info("userRequest.clientRegistration.getRegistrationId : {}", userRequest.getClientRegistration().getRegistrationId());
            log.info("userRequest.clientRegistration.getAuthorizationGrantType : {}", userRequest.getClientRegistration().getAuthorizationGrantType());
            log.info("userRequest.clientRegistration.getClientAuthenticationMethod : {}", userRequest.getClientRegistration().getClientAuthenticationMethod());
            log.info("userRequest.clientRegistration.getProviderDetails : {}", userRequest.getClientRegistration().getProviderDetails());
            log.info("userRequest.clientRegistration..getScopes : {}", userRequest.getClientRegistration().getScopes());
    
            String clientName = userRequest.getClientRegistration().getClientName();
            OAuth2User user =  super.loadUser(userRequest);
            return new CustomOAuth2User(user,clientName);
        }
     
    }


  COSE DA FARE CON IL FRONTEND
  - nella visualizzazione dell'estate indicare lo stato di vendita e le info delle stats
  - nei filtri, locality non deve restare vuoto
  - Accedo da customer ma non mi rispedisce nella home
  - Quando disconnetto la notifica subito scompare
  - Quando creo estate, l'agent non visualizza il messaggio di successo
  - la home ha i pulsanti non standard
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    FUNZIONE AMMINISTRATORE
  - quando si crea un amministratore, da frontend non scelgo la password, nel backend inserisci nel campo password "default" o come vuoi, ma una parola standard. Ovviamente non rispetta le norme di password sicura (bisognerebbe bypassarlo solo in questa casistica). Nel frontend quando accedo con password "default" mi deve obbligatoriamente far cambiare password, altrimenti non mi fa accedere a nessun servizio. Quello che dovresti fare tu da backend, oltre al fatto di settare password "default" è quella che se l'amministratore non ha ancora cambiato la password non può eseguire alcuna operazione.
  -Solo l'amministratore principale, quello creato con id=1 può creare amministratori, l'altra volta ti proposi di fare questo controllo nel backend invece che controllare io da frontend (perchè l'id dovresti mandarlo nel token, quindi poi dovresti mandarmi l'id di tutti). L'idea è quella che nel caso l'id dell'admin sia 1, al vettore role aggiungi un ruolo tipo admin-principal oltre al ruolo admin, così che io controllo dal vettore di ruoli (che già mi mandi nel token) se posso visualizzare l'opzione di creare nuovo admin


  
   VISUALIZZAZIONE FOTO:
   -  upload-photo: per una foto va bene, implementare la gestione per più foto quindi un vettore, come input questa funzione deve ricevere l'id dal frontend, così che poi associa le foto all'estate corretto. Inoltre mettere il codice che controlla che il vettore di foto che ricevi in input abbiano un minimo di dimensione 3 e un massimo di 10.
   - get-photo: va bene la visualizzazione delle foto, mi serve però che questa richiesta recuperi tutte le foto di un immobile dato in input il suo id.
   - create-real-estate-rent/sell : per entrambi i metodi, nella creazione dell'estate voglio che mi invii il proprio id (in questo  modo posso inserire nel database le foto associando le foto all'estate attraverso il proprio id)
   
   
   
   
    /*
      Richiedo il numero di operazioni completate da un'agente, per ogni mese.
      Nel body va un vettore di interi di 12 celle, ogni cella corrisponde ad un mese.
      url: /agent/{username}/number-closed-estate
    */

    numberOfClosedEstate(user:string):Observable<number[]>{
      const url = this.url+`/${user}/number-closed-estate`
      return this.http.get<number[]>(url, this.httpOptions)
    }

    /*
      Richiesto le statistiche generali di un agente immobiliare.
      Mi aspetto un oggetto AgentGeneralStats che ha i seguenti attributi:
      -uploadedNumber
      -soldEstates
      -rentedEstates
      -salesIncome
      -rentalsIncome

      url: agent/{username}/general-stats
    */
    agentStats(user:string):Observable<AgentGeneralStats>{
      const url = this.url+`/${user}/general-stats`
      return this.http.get<AgentGeneralStats>(url, this.httpOptions)
    }


     /*
      Richiesto le statistiche per ogni immobile di un certo agente immobiliare.
      Passo come valore di query, il numero di pagina e limit
      Mi aspetto un vettore di oggetti EstateStats che ha i seguenti attributi:
      -title
      -uploadDate
      -offerNumber
      -viewNumber
      -id (l'id dell'estate, serve perchè cliccando sulla riga della tabella ti reindirizza nel dettaglio dell'estate)

      url: agent/{username}/estates-stats?page=${page}&limit=${limit}
    */
    estatesStats(user:string, page:number, limit:number):Observable<EstateStats[]>{
      const url = this.url+`/${user}/estates-stats?page=${page}&limit=${limit}`
      return this.http.get<EstateStats[]>(url, this.httpOptions)
    }





































/*****************************************************************************************************************************************************/

lista  estate più recenti di un certo agente (dato l'username e un valori limit che indica il numero di estate da restituire). Voglio che mi restituisca descrizione, titolo, data e id

Dato determinati filtri, numero di pagina e limit che indica il numero di estate da restituire passami le foto, descrizione, data, titolo, id e città (da considerare se conviene inviare tutte le info dei 10 immobili o meglio inviare solo certe info)

dato un certo id resituisci tutte le info di tale estate

 
metti google
cambiare validatorEmail

Per rendere la ricerca più efficiente è possibile attraverso la tecnica della paginazione (lazy loading)

 Caricare in modo asincrono le info, prima titolo e descrizione. e poi le foto. Vedere come
 Usare asincronismo dal server per ottenere le info degli estate
 Per quanto riguarda estate, mettere price generael
(piccola chicca, vietare di far inserire una password uguale a quella precedente




# INGSW-DietiEstates2025


    /*
    *PasswordRequest ha tre parametri, vecchia e nuova password e username dell'admin. Nel backend si deve controllare se il token
    *di autenticazione passato nella request è valido, bisogna controllare se l'user ricavato dal token, corrisponde all'user presente
    *nel body. Bisogna controllare se la vecchia password corrisponde a quella reale. Bisogna testare se la password è sicura (doppio controllo,
    *tale controllo è fatto in primis nel frontend).
    *Se una di tali condizioni non è valida bisogna inviare nella response il messaggio preciso di errore associato al codice corretto
    * 
    */


Inserimento di un'appartamento in vendita:
url: /agent/{username}/create-sell-estate
cosa passo:
-estate: Estate,
-notaryDeedState: string,
-sellingPrice:number

Inserimento di un'appartamento in affitto:
url: /agent/{username}/create-rent-estate
cosa passo:
-estate: Estate,
-contractYears: number,
-rentalPrice: number,
-securityDeposit: number


Oggetto Estate:
-address: Address,
-locationFeatures: EstateLocationFeatures,
-info: EstateDescribe,
-features: EstateFeatures

Oggetto Address:
-housenumber: number,
-street: string,
-state: string,
-postcode: string,
-city: string,
-country: string,
-lon: string,
-lat: string

Oggetto EstateLocationFeatures:
-isNearPark: boolean,
-isNearPublicTransport: boolean,
-isNearSchool: boolean

Oggetto EstateDescribe:
-description: string,
-size: number,
-roomsNumber: number,
-floorNumber: number,
-energyClass: string,
-parkingSpacesNumber: number,
-condoFee: number,
-furnitureState: string,
-houseState: string,

Oggetto EstateFeatures:
-hasHeating: boolean,
-hasConcierge: boolean,
-hasAirConditioning: boolean,
-hasTerrace: boolean,
-hasGarage: boolean,
-hasBalcony: boolean,
-hasGarden: boolean,
-hasSwimmingPool: boolean

=======
# DietiEstatesFrontend

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 18.0.3.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
>>>>>>> origin/master
