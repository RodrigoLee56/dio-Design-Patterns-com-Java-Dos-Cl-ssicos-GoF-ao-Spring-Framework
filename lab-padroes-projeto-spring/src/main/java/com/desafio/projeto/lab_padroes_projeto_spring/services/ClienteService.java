package com.desafio.projeto.lab_padroes_projeto_spring.services;

import com.desafio.projeto.lab_padroes_projeto_spring.model.Cliente;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author falvojr
 */

public interface ClienteService {
	Iterable<Cliente> findAll();

	Cliente findById(Long id);

	void insert(Cliente cliente);

	void update(Long id, Cliente cliente);

	void delete(Long id);
}
