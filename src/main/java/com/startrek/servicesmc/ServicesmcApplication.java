package com.startrek.servicesmc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.api")
@PropertySource(value = { "classpath:application-prod.properties" })
@SpringBootApplication
public class ServicesmcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServicesmcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
