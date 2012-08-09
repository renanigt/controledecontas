package br.com.controledecontas.controller;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.controledecontas.controller.LoginController;
import br.com.controledecontas.controller.UsuarioLoginController;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.service.UsuarioService;

public class LoginControllerTest {
	
	private Result result;
	private LoginController loginController;
	private UsuarioLoginController usuarioLoginController;
	
	@Mock
	private UsuarioService usuarioService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		usuarioLoginController = new UsuarioLoginController();
		loginController = new LoginController(result, usuarioService, usuarioLoginController);
	}
	
	public void deveriaAbrirTelaDeLogin() {
		loginController.login();
		assertFalse("Não deveria conter erros.", result.included().containsKey("erros"));
	}
	
	@Test
	public void daveriaEfetuarLoginNaAplicacao() {
		String username = "montenegro";
		String password = "123";
		
		seUsuarioEhCadastrado(username, password);
		
		loginController.logar(username, password);
		
		assertNotNull("Não deveria ser nulo.", usuarioLoginController.getUsuario());
		assertTrue("Deveria estar logado.", usuarioLoginController.isLogado());
		assertFalse("Não deveria conter erros.", result.included().containsKey("erros"));
	}
	
	private void seUsuarioEhCadastrado(String username, String password) {
		Usuario usuario = new Usuario();
		
		usuario.setUsername(username);
		usuario.setPassword(password);
		
		when(usuarioService.valida(username, password)).thenReturn(usuario);
	}
	
}
