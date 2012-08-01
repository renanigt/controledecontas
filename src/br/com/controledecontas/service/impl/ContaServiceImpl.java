package br.com.controledecontas.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import br.com.controledecontas.model.Conta;
import br.com.controledecontas.service.ContaService;

@Service("contaService")
public class ContaServiceImpl implements ContaService {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Conta pesquisaPorId(Integer id) {
		return entityManager.find(Conta.class, id);
	}

	public void salva(Conta conta) {
		entityManager.persist(conta);
	}

}
