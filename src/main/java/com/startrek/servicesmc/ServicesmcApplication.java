package com.startrek.servicesmc;

import java.text.SimpleDateFormat;
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
import com.startrek.servicesmc.domain.ItemPedido;
import com.startrek.servicesmc.domain.Pagamento;
import com.startrek.servicesmc.domain.PagamentoComBoleto;
import com.startrek.servicesmc.domain.PagamentoComCartao;
import com.startrek.servicesmc.domain.Pedido;
import com.startrek.servicesmc.domain.Produto;
import com.startrek.servicesmc.domain.enums.EstadoPagamento;
import com.startrek.servicesmc.domain.enums.TipoCliente;
import com.startrek.servicesmc.repositories.CategoriaRepository;
import com.startrek.servicesmc.repositories.CidadeRepository;
import com.startrek.servicesmc.repositories.ClienteRepository;
import com.startrek.servicesmc.repositories.EnderecoRepository;
import com.startrek.servicesmc.repositories.EstadoRepository;
import com.startrek.servicesmc.repositories.ItemPedidoRepository;
import com.startrek.servicesmc.repositories.PagamentoRepository;
import com.startrek.servicesmc.repositories.PedidoRepository;
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
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ServicesmcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Apoio para notebook");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoracao");
		Categoria cat7 = new Categoria(null, "MousePad");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
			
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("25/03/2021 15:29"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("26/03/2021 14:29"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE,
				ped2, sdf.parse("20/10/2021 15:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItems().addAll(Arrays.asList(ip1,ip2));
		ped2.getItems().addAll(Arrays.asList(ip3));
		
		p1.getItems().addAll(Arrays.asList(ip1));
		p2.getItems().addAll(Arrays.asList(ip3));
		p3.getItems().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	
	}
}
