package br.com.controledecontas.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.model.UsuarioSession;
import br.com.controledecontas.service.UsuarioService;

public class LoginControllerTest {
	
	private Result result;
	private LoginController loginController;
	private UsuarioSession usuarioSession;
	
	@Mock
	private UsuarioService usuarioService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		usuarioSession = new UsuarioSession();
		loginController = new LoginController(result, usuarioService, usuarioSession);
	}
	
	@Test
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
		
		assertTrue("Deveria estar logado.", usuarioSession.isLogado());
		assertFalse("Não deveria conter erros.", result.included().containsKey("erros"));
	}
	
	@Test
	public void naoDeveriaEfetuarLoginParaUsuarioNaoCadastrado() {
		when(usuarioService.autentica(anyString(), anyString())).thenReturn(null);
		
		loginController.logar("teste", "teste");
		
		assertFalse("Não deveria estar logado.", usuarioSession.isLogado());
		assertTrue("Não deveria conter mensagem de erro", result.included().containsKey("erros"));
	}
	
	private void seUsuarioEhCadastrado(String username, String password) {
		Usuario usuario = new Usuario();
		
		usuario.setUsername(username);
		usuario.setPassword(password);
		
		when(usuarioService.autentica(username, password)).thenReturn(usuario);
	}
	
}
