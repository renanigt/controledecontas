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

	public void atualiza(Usuario usuario) {
		Usuario usuarioAlterado = this.pesquisaPorId(usuario.getId());
		usuarioAlterado.setNome(usuario.getNome());
		usuarioAlterado.setLogin(usuario.getLogin());
		usuarioAlterado.setSenha(usuario.getSenha());
		
		entityManager.merge(this.pesquisaPorId(usuario.getId()));
	}
	
	public Usuario autentica(String login, String senha) {
		String hql = "from Usuario where username = :username and password = :password";
		
		Query query = entityManager.createQuery(hql);
	
		query.setParameter("username", login);
		query.setParameter("password", senha);
		
		try {
			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public String pesquisarSenha(Integer id) {
		String hql = "select senha from Usuario where id = :id";
		
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("id", id);
		
		try {
			return query.getSingleResult().toString();
		} catch (NoResultException e) {
			return null;
		}
	}

	
}
