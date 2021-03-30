package com.startrek.servicesmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.startrek.servicesmc.domain.Categoria;
import com.startrek.servicesmc.dto.CategoriaDTO;
import com.startrek.servicesmc.repositories.CategoriaRepository;
import com.startrek.servicesmc.services.exceptions.DateIntegrityException;
import com.startrek.servicesmc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repp;
	
	public Categoria search(Integer id) {
		Optional<Categoria> obj = repp.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException
		("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));	
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repp.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		search(obj.getId());
		return repp.save(obj);
	}
	
	public void delete(Integer id) {
		search(id);
		try {
			repp.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DateIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repp.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repp.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(),objDto.getNome());
	}
}
