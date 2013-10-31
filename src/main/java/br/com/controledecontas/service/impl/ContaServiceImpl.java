package br.com.controledecontas.service.impl;

import java.util.Collections;
import java.util.Date;
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
		Usuario usuario = conta.getUsuario();
		usuario.alteraSaldo(conta, false);
		
		entityManager.persist(conta);
		entityManager.merge(usuario);
	}

	public void deleta(Conta conta) {
		Usuario usuario = conta.getUsuario();
		usuario.alteraSaldo(conta, true);
		
		entityManager.remove(conta);
		entityManager.merge(usuario);
	}

	public void atualiza(Conta conta, Conta contaAnterior) {
		Usuario usuario = conta.getUsuario();
		
		usuario.alteraSaldo(contaAnterior, true);
		usuario.alteraSaldo(conta, false);
		
		entityManager.merge(conta);
		entityManager.merge(usuario);
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
		
		String hql = "from Conta where usuario_id = :userId and descricao like :descricao";
		
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("userId", usuario.getId());
		query.setParameter("descricao", "%" + descricao + "%");
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Conta> pesquisarPorPeriodo(Usuario usuario, Date dataInicio, Date dataFim) {
		if (usuario == null) {
			return Collections.emptyList();
		}
		
		String hql = "from Conta where usuario_id = :userId and data between DATE(:dataInicio) and DATE(:dataFim) order by data";
		
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("userId", usuario.getId());
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		
		return query.getResultList();
	}

}
