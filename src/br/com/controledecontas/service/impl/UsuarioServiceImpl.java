package br.com.controledecontas.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import br.com.controdecontas.service.UsuarioService;
import br.com.controledecontas.model.Usuario;

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
	
}
