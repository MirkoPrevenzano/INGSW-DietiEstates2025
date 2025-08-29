package com.dietiestates.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig 
{
    /*@Bean
    public OpenAPI customOpenAPI() 
    {
        return new OpenAPI().info(new Info().title("Dieti Estates API")
                                            .version("1.0")
                                            .description("Esempio di integrazione Spring Boot + OpenAPI + Swagger UI"));
    }*/

    
    @Bean
    public OpenAPI customOpenAPI() 
    {
        return new OpenAPI().info(new Info().title("Dieti Estates API")
                                            .version("1.0")
                                            .description("Lista delle API messe a disposizione dalla nostra app per la gestione e/o compravendita di proprietà immobiliari")
                                            .contact(new Contact().name("Ciro")
                                                                  .email("ciro@example.com")
                                                                  .url("http://localhost:8080")))
                            .servers(List.of(new Server().url("http://localhost:8080")
                                                         .description("Server locale"), 
                                             new Server().url("https://")
                                                         .description("Server di produzione")))
                            .tags(List.of(new Tag().name("Users")
                                                   .description("Operazioni sugli utenti")));
    }
}