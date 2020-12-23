package com.jncode.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jncode.springboot.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public void Save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void detete(Long id);
	
}
