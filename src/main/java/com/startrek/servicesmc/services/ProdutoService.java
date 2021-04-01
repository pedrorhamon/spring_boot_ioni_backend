package com.startrek.servicesmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.startrek.servicesmc.domain.Categoria;
import com.startrek.servicesmc.domain.Produto;
import com.startrek.servicesmc.repositories.CategoriaRepository;
import com.startrek.servicesmc.repositories.ProdutoRepository;
import com.startrek.servicesmc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repp;
	
	@Autowired
	private CategoriaRepository cate;
	
	public Produto search(Integer id) {
		Optional<Produto> obj = repp.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException
		("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));	
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage,
			String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		List<Categoria> categorias = cate.findAllById(ids);
		return repp.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}
}
