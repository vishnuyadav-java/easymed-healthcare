package com.easymed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.easymed")
public class EasyMedHealthcareApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyMedHealthcareApplication.class, args);
	}

}
