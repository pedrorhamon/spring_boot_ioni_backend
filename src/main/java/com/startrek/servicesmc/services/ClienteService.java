package com.startrek.servicesmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.startrek.servicesmc.domain.Cliente;
import com.startrek.servicesmc.dto.ClienteDTO;
import com.startrek.servicesmc.repositories.ClienteRepository;
import com.startrek.servicesmc.services.exceptions.DateIntegrityException;
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
	
	public Cliente update(Cliente obj) {
		Cliente newObj = search(obj.getId());
		updateData(newObj, obj);
		return repp.save(newObj);
	}
	
	public void delete(Integer id) {
		search(id);
		try {
			repp.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DateIntegrityException("Não é possivel excluir à entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll(){
		return repp.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repp.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());		
	}
}
