package br.com.controledecontas.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class IndexControllerTest {

	private Result result;
	private IndexController indexController;
	
	@Before
	public void setup() {
		result = new MockResult();
		indexController = new IndexController(result);
	}
	
	@Test
	public void deveriaAbrirTelaInicialComMensagem() {
		indexController.index();
		
		assertTrue("Deveria conter mensagem de sucesso.", result.included().containsKey("notice"));
		assertFalse("Não deveria conter mensagem de erro.", result.included().containsKey("erros"));
	}
	
}
