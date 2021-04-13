package com.startrek.servicesmc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.startrek.servicesmc.domain.Cliente;
import com.startrek.servicesmc.repositories.ClienteRepository;
import com.startrek.servicesmc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	private ClienteRepository clienteRepository;
	private BCryptPasswordEncoder bc;

	private Random random = new Random();

	@Autowired
	private EmailService emailService;

	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		String newPass = newPassword();
		cliente.setSenha(bc.encode(newPass));

		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if (opt == 0) { // gera digitos
			return (char) (random.nextInt(10) + 48);
		} else if (opt == 1) { // gera letras caixa Alta
			return (char) (random.nextInt(26) + 65);
		} else { // gera letras minuscula
			return (char) (random.nextInt(26) + 997);

		}

	}

}
