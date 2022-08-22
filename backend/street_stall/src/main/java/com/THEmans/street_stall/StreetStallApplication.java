package com.THEmans.street_stall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class StreetStallApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreetStallApplication.class, args);

	}

}
