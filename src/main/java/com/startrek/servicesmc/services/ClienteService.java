package com.startrek.servicesmc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startrek.servicesmc.domain.Cliente;
import com.startrek.servicesmc.repositories.ClienteRepository;
import com.startrek.servicesmc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repp;
	
	public Cliente search(Integer id) {
		Optional<Cliente> obj = repp.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException
		("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));	
	}
}
