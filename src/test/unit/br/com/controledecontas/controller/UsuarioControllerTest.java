package br.com.controledecontas.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.util.test.MockLocalization;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.model.UsuarioSession;
import br.com.controledecontas.service.UsuarioService;

public class UsuarioControllerTest {

	private Result result;
	private UsuarioController usuarioController;
	private Validator validator;
	private Localization localization;
	
	@Mock
	private UsuarioSession usuarioSession;
	@Mock
	private UsuarioService usuarioService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		validator = new MockValidator();
		localization = new MockLocalization();
		usuarioController = new UsuarioController(result, usuarioService, validator, localization, usuarioSession);
	}

	@Test
	public void deveriaAbrirPaginaDeCadastroDeUsuario() {
		usuarioController.novo();
		
		assertFalse("Não deveria conter erros.", result.included().containsKey("erros"));
	}
	
	@Test
	public void deveriaAbrirPaginaDeAtualizacaoComUsuarioLogado() {
		when(usuarioSession.isLogado()).thenReturn(true);
		
		usuarioController.edita(1);
		
		assertTrue("Deve conter um usuario", result.included().containsKey("usuario"));
	}

	@Test
	public void naoDeveriaAbrirPaginaDeAtualizacaoSemUsuarioLogado() {
		when(usuarioSession.isLogado()).thenReturn(false);
		
		usuarioController.edita(1);
		
		assertFalse("Deve conter um usuario", result.included().containsKey("usuario"));
	}
	
	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = aquinofb();
		
		usuarioController.salva(usuario);
		
		verify(usuarioService).salva(usuario);
		
		assertEquals(localization.getMessage("usuario.salvo.sucesso"), result.included().get("notice"));
		assertFalse("Não deve conter erros.", result.included().containsKey("erros"));
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveriaSalvarUsuarioVazio() {
		Usuario usuario = criaUsuarioVazio();
		
		usuarioController.salva(usuario);
	}
	
	@Test
	public void naoDeveriaSalvarUsuarioException() {
		Usuario usuario = aquinofb();
		
		doThrow(new RuntimeException()).when(usuarioService).salva(usuario);
		
		usuarioController.salva(usuario);
		
		assertTrue("Deveria conter mensagem de erro.", result.included().containsKey("erros"));
		assertFalse("Não deveria conter mesagem de sucesso.", result.included().containsKey("notice"));
	}
	
	@Test
	public void deveriaAtualizarUsuario() {
		Usuario usuario = renanigt();
		
		usuarioController.atualiza(usuario);
		verify(usuarioService).atualiza(usuario);
		
		assertEquals(localization.getMessage("usuario.atualizado.sucesso"), result.included().get("notice"));
		assertFalse("Não deve conter erros.", result.included().containsKey("erros"));
	}
	
	@Test(expected=ValidationException.class)
	public void naoDeveriaAtualizarUsuarioVazio() {
		Usuario usuario = criaUsuarioVazio();
		
		usuarioController.atualiza(usuario);
	}
	
	@Test
	public void naoDeveriaAtualizarUsuarioException() {
		Usuario usuario = renanigt();
		
		doThrow(new RuntimeException()).when(usuarioService).atualiza(usuario);
		
		usuarioController.atualiza(usuario);
		
		assertTrue("Deveria conter mensagem de erro.", result.included().containsKey("erros"));
		assertFalse("Não deveria conter mesagem de sucesso.", result.included().containsKey("notice"));
	}
	
	private Usuario aquinofb() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("Felipe Aquino");
		usuario.setUsername("aquinofb");
		usuario.setPassword("321");
		
		return usuario;
	}
	
	private Usuario criaUsuarioVazio() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("");
		usuario.setUsername("");
		usuario.setPassword("");
		
		return usuario;
	}

	private Usuario renanigt() {
		Usuario usuario = new Usuario();
		
		usuario.setId(1);
		usuario.setNome("Renan Montenegro");
		usuario.setUsername("renanigt");
		usuario.setPassword("123");
		
		return usuario;
	}

}
