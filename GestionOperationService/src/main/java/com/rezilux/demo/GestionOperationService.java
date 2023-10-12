package com.rezilux.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class GestionOperationService {

	public static void main(String[] args) {
		SpringApplication.run(GestionOperationService.class, args);
	}

}
