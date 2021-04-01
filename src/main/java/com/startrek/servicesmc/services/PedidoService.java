package com.startrek.servicesmc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startrek.servicesmc.domain.ItemPedido;
import com.startrek.servicesmc.domain.PagamentoComBoleto;
import com.startrek.servicesmc.domain.Pedido;
import com.startrek.servicesmc.domain.enums.EstadoPagamento;
import com.startrek.servicesmc.repositories.ItemPedidoRepository;
import com.startrek.servicesmc.repositories.PagamentoRepository;
import com.startrek.servicesmc.repositories.PedidoRepository;
import com.startrek.servicesmc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repp;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido search(Integer id) {
		Optional<Pedido> obj = repp.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException
		("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));	
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repp.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItems()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.search(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItems());
		return obj;
	}
}
