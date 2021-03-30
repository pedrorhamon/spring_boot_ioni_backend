package com.startrek.servicesmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.startrek.servicesmc.domain.Cidade;
import com.startrek.servicesmc.domain.Cliente;
import com.startrek.servicesmc.domain.Endereco;
import com.startrek.servicesmc.domain.enums.TipoCliente;
import com.startrek.servicesmc.dto.ClienteDTO;
import com.startrek.servicesmc.dto.ClienteNewDTO;
import com.startrek.servicesmc.repositories.ClienteRepository;
import com.startrek.servicesmc.repositories.EnderecoRepository;
import com.startrek.servicesmc.services.exceptions.DataIntegrityException;
import com.startrek.servicesmc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repp;
	
	@Autowired
	private EnderecoRepository ender;
	
	public Cliente search(Integer id) {
		Optional<Cliente> obj = repp.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException
		("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));	
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repp.save(obj);
		ender.saveAll(obj.getEnderecos());
		return obj;
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
			throw new DataIntegrityException("Não é possivel excluir à entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll(){
		return repp.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repp.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), 
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());		
	}
}
