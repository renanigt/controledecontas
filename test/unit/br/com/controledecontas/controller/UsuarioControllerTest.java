package br.com.controledecontas.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.service.UsuarioService;

public class UsuarioControllerTest {

	private Result result;
	private UsuarioController usuarioController;
	private Validator validator;
	
	@Mock
	private UsuarioService usuarioService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		validator = new MockValidator();
		usuarioController = new UsuarioController(result, usuarioService, validator);
	}

	@Test
	public void deveriaAbrirTelaDeCadastroDeUsuario() {
		usuarioController.novo();
		assertFalse("Não deveria conter erros.", result.included().containsKey("erros"));
	}
	
	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = criaUsuario();
		
		usuarioController.salva(usuario);
		
		verify(usuarioService).salva(usuario);
		
		assertTrue("Não deve conter erros.", result.included().containsKey("notice"));
		assertFalse("Não deve conter erros.", result.included().containsKey("erros"));
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveriaSalvarUsuarioVazio() {
		Usuario usuario = criaUsuarioVazio();
		
		usuarioController.salva(usuario);
	}
	
	private Usuario criaUsuario() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("Felipe Aquino");
		usuario.setUsername("aquinofb");
		usuario.setPassword("321");
		
		return usuario;
	}
	
	private Usuario criaUsuarioVazio() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("");
		usuario.setPassword("");
		usuario.setUsername("");
		
		return usuario;
	}

}
