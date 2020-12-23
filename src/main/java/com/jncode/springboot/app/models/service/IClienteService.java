package com.jncode.springboot.app.models.service;

import java.util.List;

import com.jncode.springboot.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public void Save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void detete(Long id);
	
}
