package com.startrek.servicesmc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import com.startrek.servicesmc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private ITemplateEngine itemplateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		senderEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido autorizado! Codigo: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return null;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return itemplateEngine.process("email/confirmacaoPedido", context);
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		MimeMessage mm;
		try {
			mm = prepareMineMailMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMineMailMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Codigo:" + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mm;
	}
}
