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
	public List<Conta> pesquisaPorMesEAno(Usuario usuario, Integer mes, Integer ano) {
		if (usuario == null) {
			return Collections.emptyList();
		}
		
		String hql = "from Conta where usuario_id = :userId and month(data) = :mes and year(data) = :ano order by data";
		
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("userId", usuario.getId());
		query.setParameter("mes", mes + 1);
		query.setParameter("ano", ano);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Conta> pesquisaPorDescricao(Usuario usuario, String descricao) {
		if (usuario == null) {
			return Collections.emptyList();
		}
		
		String hql = "from Conta where descricao like :descricao";
		
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("descricao", "%" + descricao + "%");
		
		return query.getResultList();
	}

}
