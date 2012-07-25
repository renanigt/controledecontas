package br.com.controledecontas.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import br.com.controdecontas.service.ContaService;
import br.com.controledecontas.model.Conta;

@Service("contaService")
public class ContaServiceImpl implements ContaService {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Conta buscarPorId(Integer id) {
		return entityManager.find(Conta.class, id);
	}

}
