package com.ynov.microservices.tlm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AuthorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorServiceApplication.class, args);
	}

}
