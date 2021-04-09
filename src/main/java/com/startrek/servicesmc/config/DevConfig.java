package com.startrek.servicesmc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.startrek.servicesmc.services.DBService;
import com.startrek.servicesmc.services.EmailService;
import com.startrek.servicesmc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String atrategy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		if(!"create".equals(atrategy)) {
			return false;
		}
		
		dbService.instantiateTestDB();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
