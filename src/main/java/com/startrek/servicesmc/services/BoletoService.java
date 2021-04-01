package com.startrek.servicesmc.services;

import java.util.Calendar;
import java.util.Date;

import com.startrek.servicesmc.domain.PagamentoComBoleto;

public class BoletoService {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instantePedido) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(instantePedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}

}
