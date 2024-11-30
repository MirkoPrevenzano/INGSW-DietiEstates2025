package com.example.datatier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class DataTierServerApplication {

	public static void main(String[] args) {
		/*Cliente cliente=new Cliente();
		cliente.setCognome("Prevenzano");
		cliente.setNome("Mirko");
		cliente.setEmail("ci");
		cliente.setPassword("ccc");
		ClienteService clienteService=new ClienteService();
		clienteService.saveCliente(cliente);*/
		SpringApplication.run(DataTierServerApplication.class, args);
		

	}

}
