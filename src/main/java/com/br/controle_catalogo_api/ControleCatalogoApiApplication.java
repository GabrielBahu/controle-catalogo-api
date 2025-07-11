package com.br.controle_catalogo_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories()
public class ControleCatalogoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleCatalogoApiApplication.class, args);
	}

}
