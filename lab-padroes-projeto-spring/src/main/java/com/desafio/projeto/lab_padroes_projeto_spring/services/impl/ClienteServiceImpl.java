package com.desafio.projeto.lab_padroes_projeto_spring.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.projeto.lab_padroes_projeto_spring.model.Cliente;
import com.desafio.projeto.lab_padroes_projeto_spring.model.Endereco;
import com.desafio.projeto.lab_padroes_projeto_spring.repository.ClienteRepository;
import com.desafio.projeto.lab_padroes_projeto_spring.repository.EnderecoRepository;
import com.desafio.projeto.lab_padroes_projeto_spring.services.ClienteService;
import com.desafio.projeto.lab_padroes_projeto_spring.services.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */

@Service
public class ClienteServiceImpl implements ClienteService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ViaCepService viaCepService;

	@Override
	public Iterable<Cliente> findAll() {
		// TODO Auto-generated method stub
		return clienteRepository.findAll();
	}

	@Override
	public Cliente findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void insert(Cliente cliente) {
		// TODO Auto-generated method stub
		salvarClienteComCep(cliente);
	}

	@Override
	public void update(Long id, Cliente cliente) {
		// TODO Auto-generated method stub
		Optional<Cliente> clienteById = clienteRepository.findById(id);
		if (clienteById.isPresent()) {
			salvarClienteComCep(cliente);
		}
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteRepository.deleteById(id);
	}

	private void salvarClienteComCep(Cliente cliente) {
		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco newAdress = viaCepService.findCep(cep);
			enderecoRepository.save(newAdress);
			return newAdress;
		});
		cliente.setEndereco(endereco);
		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		clienteRepository.save(cliente);
	}

}
