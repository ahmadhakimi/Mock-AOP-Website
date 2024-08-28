package com.elf.crud;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		SpringApplication.run(CrudApplication.class, args);
	}

}
