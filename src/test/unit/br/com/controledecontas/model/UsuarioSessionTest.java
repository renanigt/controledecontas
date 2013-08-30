package br.com.controledecontas.model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.model.UsuarioSession;

public class UsuarioSessionTest {

	UsuarioSession usuarioSession;
	
	@Before
	public void setup() {
		usuarioSession = new UsuarioSession();
	}
	
	@Test
	public void deveriaLogarUsuario() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("Renan");
		usuario.setUsername("renaniguatu");
		usuario.setPassword("teste");
		
		usuarioSession.setUsuario(usuario);
		
		assertNotNull("Usuário não deve ser nulo.", usuario);
		assertTrue("Usuário não deve ser nulo.", usuarioSession.isLogado());
	}
	
	@Test
	public void deveriaEfetuarLogout() {
		logaUsuario();
		
		usuarioSession.logout();
		
		assertNull("Usuário deve ser nulo.", usuarioSession.getUsuario());
		assertFalse("Usuário deve ser nulo.", usuarioSession.isLogado());
	}

	private void logaUsuario() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("Felipe");
		usuario.setUsername("aquinofb");
		usuario.setPassword("teste");
		
		usuarioSession.setUsuario(usuario);
	}
	
}
