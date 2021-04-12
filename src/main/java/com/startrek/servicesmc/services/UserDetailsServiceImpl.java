package com.startrek.servicesmc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.startrek.servicesmc.domain.Cliente;
import com.startrek.servicesmc.repositories.ClienteRepository;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ClienteRepository repp;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Cliente cli = repp.findByEmail(email);
		if(cli == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return null;
	}

}
