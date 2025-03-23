
  COSE DA FARE CON IL FRONTEND
  - nella visualizzazione dell'estate indicare lo stato di vendita e le info delle stats
  - nei filtri, locality non deve restare vuoto
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
