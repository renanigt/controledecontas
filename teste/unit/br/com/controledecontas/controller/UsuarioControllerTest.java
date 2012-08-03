package br.com.controledecontas.controller;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.controledecontas.service.UsuarioService;

public class UsuarioControllerTest {

	private Result result;
	private UsuarioController usuarioController;
	
	@Mock
	private UsuarioService usuarioService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		usuarioController = new UsuarioController(result, usuarioService);
	}

	@Test
	public void deveriaAbrirTelaDeCadastroDeUsuario() {
		usuarioController.novo();
		assertFalse("Não deveria conter erros.", result.included().containsKey("erros"));
	}
	
}
