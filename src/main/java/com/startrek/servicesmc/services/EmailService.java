package com.startrek.servicesmc.services;

import org.springframework.mail.SimpleMailMessage;

import com.startrek.servicesmc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void senderEmail(SimpleMailMessage msg);

}
