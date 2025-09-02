
package com.dietiestates.backend.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig 
{
    @Bean
    public OpenAPI customOpenAPI() 
    {
        return new OpenAPI().info(new Info().title("Dieti Estates API")
                                            .version("1.0")
                                            .description("API per la piattaforma Dieti Estates, che consente la gestione e la compravendita di proprietà immobiliari.")
                                            .contact(new Contact().name("Ciro")
                                                                  .email("ciropizza2002@gmail.com")
                                                                  .url("http://localhost:8080")))
                            .components(new Components().addSecuritySchemes("jwtBearerAuth", new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                                                                                                 .in(SecurityScheme.In.HEADER)
                                                                                                                 .scheme("bearer")
                                                                                                                 .bearerFormat("JWT")
                                                                                                                 .description("Autorizzazione tramite token JWT, necessaria per accedere alle API protette"))
                                                         .addSchemas("jwtTokenResponse", new Schema<>().type("object")
                                                                                                       .addProperty("accessToken", new Schema<>().type("string")
                                                                                                                                                 .description("Token JWT generato al momento dell'autenticazione, utilizzato per autorizzare le richieste successive.")
                                                                                                                                                 .example("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."))))
                            .servers(List.of(new Server().url("http://localhost:8080")
                                                         .description("Server locale"), 
                                             new Server().url("https://")
                                                         .description("Server di produzione")))
                            .tags(List.of(new Tag().name("Authentication")
                                                   .description("Operazioni di autenticazione"),
                                          new Tag().name("Agencies")
                                                   .description("Operazioni sulle agencies"),
                                          new Tag().name("Admins")
                                                   .description("Operazioni sugli admins"),
                                          new Tag().name("Agents")
                                                   .description("Operazioni sugli agents"),
                                          new Tag().name("Customers")
                                                   .description("Operazioni sui customers"),
                                          new Tag().name("Users")
                                                   .description("Operazioni sugli utenti"),
                                          new Tag().name("Real Estates")
                                                   .description("Operazioni sui real estates")))
                            .security(List.of(new SecurityRequirement().addList("jwtBearerAuth")))
                            .path("/login", createLoginPath());
    }


    private PathItem createLoginPath()
    {
         return new PathItem().post(new Operation().summary("Login utente")
                                                   .description("Autenticazione tramite login classico, con campi username, password e ruolo. Utilizza form data (application/x-www-form-urlencoded).")
                                                   .security(List.of())
                                                   .tags(List.of("Authentication"))
                                                   .requestBody(new RequestBody().required(true)
                                                                                 .description("Credenziali di accesso")
                                                                                 .content(new Content().addMediaType("application/x-www-form-urlencoded", new MediaType().schema(getLoginRequestSchema()))))
                                                   .responses(new ApiResponses().addApiResponse("200", new ApiResponse().description("Login effettuato con successo!")
                                                                                                                        .content(new Content().addMediaType("application/json", new MediaType().schema(new Schema<>().$ref("#/components/schemas/jwtTokenResponse")))))));

    }

    private Schema<?> getLoginRequestSchema()
    {
         return new Schema<>().type("object")
                              .addProperty("username", new Schema<>().type("string")
                                                                     .description("Email dell'utente")
                                                                     .format("email")
                                                                     .example("login@example.com"))
                              .addProperty("password", new Schema<>().type("string")
                                                                     .format("password")
                                                                     .description("Password dell'utente")
                                                                     .example("password1234@!"));
    }

   private Schema<?> getJwtTokenResponseSchema()
   {
         return new Schema<>().type("object")
                              .addProperty("accessToken", new Schema<>().type("string")
                                                                        .description("Token JWT da usare per l'autenticazione dell'utente.")
                                                                        .example("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."));
   }
}