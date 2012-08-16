package br.com.controledecontas.service.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import br.com.controledecontas.model.Conta;
import br.com.controledecontas.model.TipoConta;
import br.com.controledecontas.model.Usuario;
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

	public void deleta(Conta conta) {
		entityManager.remove(conta);
	}

	public void atualiza(Conta conta) {
		entityManager.merge(conta);
	}

	@SuppressWarnings("unchecked")
	public List<Conta> pesquisaPorTipo(Usuario usuario, TipoConta tipoConta) {
		if (usuario == null) {
			return Collections.emptyList();
		}
		
		String hql = "from Conta where usuario_id = :userId and tipoConta = :tipoConta";
		
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("userId", usuario.getId());
		query.setParameter("tipoConta", tipoConta);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Conta> pesquisaPorMes(Usuario usuario, Integer mes) {
		if (usuario == null) {
			return Collections.emptyList();
		}
		
		String hql = "from Conta where usuario_id = :userId and month(data) = :mes order by data";
		
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("userId", usuario.getId());
		query.setParameter("mes", mes + 1);
		
		return query.getResultList();
	}

}
