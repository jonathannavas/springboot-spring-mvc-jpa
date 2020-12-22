package com.jncode.springboot.app.models.dao;

import java.util.List;

import com.jncode.springboot.app.models.entity.Cliente;

public interface IClienteDao {

	public List<Cliente> findAll();
	
	public void Save(Cliente cliente);
	
}
