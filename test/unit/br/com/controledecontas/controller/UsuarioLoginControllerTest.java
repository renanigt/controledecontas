package br.com.controledecontas.controller;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import br.com.controledecontas.model.Usuario;

public class UsuarioLoginControllerTest {

	UsuarioLoginController usuarioLoginController;
	
	@Before
	public void setup() {
		usuarioLoginController = new UsuarioLoginController();
	}
	
	@Test
	public void deveriaLogarUsuario() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("Renan");
		usuario.setUsername("renaniguatu");
		usuario.setPassword("teste");
		
		usuarioLoginController.setUsuario(usuario);
		
		assertNotNull("Usuário não deve ser nulo.", usuario);
		assertTrue("Usuário não deve ser nulo.", usuarioLoginController.isLogado());
	}
	
	@Test
	public void deveriaEfetuarLogout() {
		logaUsuario();
		
		usuarioLoginController.logout();
		
		assertNull("Usuário deve ser nulo.", usuarioLoginController.getUsuario());
		assertFalse("Usuário deve ser nulo.", usuarioLoginController.isLogado());
	}

	private void logaUsuario() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("Felipe");
		usuario.setUsername("aquinofb");
		usuario.setPassword("teste");
		
		usuarioLoginController.setUsuario(usuario);
	}
	
}
