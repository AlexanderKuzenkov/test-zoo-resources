package com.kuzenkov.zooresources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ZooResourcesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooResourcesApplication.class, args);
	}
}
