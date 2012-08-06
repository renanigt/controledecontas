package br.com.controledecontas.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.service.UsuarioService;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@PersistenceContext
	EntityManager entityManager;

	public Usuario pesquisaPorId(Integer id) {
		return entityManager.find(Usuario.class, id);
	}

	public void salva(Usuario usuario) {
		entityManager.persist(usuario);
	}

	public Usuario valida(String username, String password) {
		String hql = "from Usuario where username = :username and password = :password";
		
		Query query = entityManager.createQuery(hql);
	
		query.setParameter("username", username);
		query.setParameter("password", password);
		
		try {
			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
