package com.startrek.servicesmc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.startrek.servicesmc.domain.Categoria;
import com.startrek.servicesmc.domain.Cidade;
import com.startrek.servicesmc.domain.Cliente;
import com.startrek.servicesmc.domain.Endereco;
import com.startrek.servicesmc.domain.Estado;
import com.startrek.servicesmc.domain.Produto;
import com.startrek.servicesmc.domain.enums.TipoCliente;
import com.startrek.servicesmc.repositories.CategoriaRepository;
import com.startrek.servicesmc.repositories.CidadeRepository;
import com.startrek.servicesmc.repositories.ClienteRepository;
import com.startrek.servicesmc.repositories.EnderecoRepository;
import com.startrek.servicesmc.repositories.EstadoRepository;
import com.startrek.servicesmc.repositories.ProdutoRepository;

@SpringBootApplication
public class ServicesmcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ServicesmcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
			
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Pernambuco");
		Estado est2 = new Estado(null, "Paraiba");
		
		Cidade c1 = new Cidade(null, "Recife", est1);
		Cidade c2 = new Cidade(null, "João Pessoa", est2);
		Cidade c3 = new Cidade(null, "Campina Grande", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Pedro Rhamon", "pedro@gmail.com","456484948498", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("98885464", "45468898"));
		
		Endereco e1 = new Endereco(null, "Rua Joao tavares ferreira", "101", "apto 105", "Joao Paulo", "54165", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Pedro tavares ferreira", "102", "apto 106", "Joao PauloII", "45932", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
	}
}
