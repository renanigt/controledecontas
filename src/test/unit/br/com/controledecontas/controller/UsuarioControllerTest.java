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
	public void deveriaAbrirPaginaDeCadastro() {
		usuarioController.novo();
		
		assertFalse("Não deveria conter erros.", result.included().containsKey("erros"));
	}
	
	@Test
	public void deveriaAbrirPaginaDeAtualizacaoPerfilComUsuarioLogado() {
		usuarioController.editaPerfil();
		
		assertTrue("Deve conter um usuario", result.included().containsKey("usuario"));
	}

	@Test
	public void deveriaAbrirPaginaDeAtualizacaoSenhaComUsuarioLogado() {
		usuarioController.editaSenha();
		
		assertTrue("Deve conter um usuario", result.included().containsKey("usuario"));
	}

	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = aquinofb();
		
		when(usuarioService.pesquisarPorLogin(usuario.getLogin())).thenReturn(null);
		
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
	
	@Test(expected=ValidationException.class)
	public void naoDeveriaSalvarUsuarioComLoginJaExistente() {
		Usuario usuario = aquinofb();
		
		when(usuarioService.pesquisarPorLogin(usuario.getLogin())).thenReturn(loginIgualAquinoFb());
		
		usuarioController.salva(usuario);
	}
	
	@Test
	public void naoDeveriaSalvarUsuarioException() {
		Usuario usuario = aquinofb();
		
		when(usuarioService.pesquisarPorLogin(usuario.getLogin())).thenReturn(null);
		
		doThrow(new RuntimeException()).when(usuarioService).salva(usuario);
		
		usuarioController.salva(usuario);
		
		assertTrue("Deveria conter mensagem de erro.", result.included().containsKey("erros"));
		assertFalse("Não deveria conter mesagem de sucesso.", result.included().containsKey("notice"));
	}
	
	@Test
	public void deveriaAtualizarPerfilDoUsuario() {
		Usuario usuario = renanigt();
		
		when(usuarioService.pesquisarPorLogin(usuario.getLogin())).thenReturn(null);
		
		usuarioController.atualizaPerfil(usuario);
		verify(usuarioService).atualiza(usuario);
		
		assertEquals(localization.getMessage("usuario.atualizado.sucesso"), result.included().get("notice"));
		assertFalse("Não deve conter erros.", result.included().containsKey("erros"));
	}

	@Test
	public void deveriaAtualizarPerfilDoUsuarioLoginIgualIdIgual() {
		Usuario usuario = renanigt();
		
		when(usuarioService.pesquisarPorLogin(usuario.getLogin())).thenReturn(usuario);
		
		usuarioController.atualizaPerfil(usuario);
		verify(usuarioService).atualiza(usuario);
		
		assertEquals(localization.getMessage("usuario.atualizado.sucesso"), result.included().get("notice"));
		assertFalse("Não deve conter erros.", result.included().containsKey("erros"));
	}

	@Test(expected=ValidationException.class)
	public void naoDeveriaAtualizarUsuarioVazio() {
		Usuario usuario = criaUsuarioVazio();
		
		usuarioController.atualizaPerfil(usuario);
	}
	
	@Test(expected=ValidationException.class)
	public void naoDeveriaAtualizarUsuarioComLoginJaExistenteIdDiferente() {
		Usuario usuario = renanigt();
		Usuario usuarioIdDiferente = renanigt();
		usuarioIdDiferente.setId(2);
		
		when(usuarioService.pesquisarPorLogin(usuario.getLogin())).thenReturn(usuarioIdDiferente);
		
		usuarioController.atualizaPerfil(usuario);
	}
	
	@Test
	public void naoDeveriaAtualizarUsuarioException() {
		Usuario usuario = renanigt();
		
		when(usuarioService.pesquisarPorLogin(usuario.getLogin())).thenReturn(null);
		doThrow(new RuntimeException()).when(usuarioService).atualiza(usuario);
		
		usuarioController.atualizaPerfil(usuario);
		
		assertTrue("Deveria conter mensagem de erro.", result.included().containsKey("erros"));
		assertFalse("Não deveria conter mensagem de sucesso.", result.included().containsKey("notice"));
	}
	
	@Test
	public void deveriaAtualizarSenhaUsuario() {
		Usuario usuario = renanigtSenhaAlterada();
		String senhaConfirm = "321";
		String senhaAtual = "123";
		
		when(usuarioService.pesquisarSenha(1)).thenReturn(renanigt().getSenha());
		
		usuarioController.atualizaSenha(usuario, senhaConfirm, senhaAtual);
		verify(usuarioService).atualiza(usuario);

		assertEquals(localization.getMessage("usuario.atualizado.sucesso"), result.included().get("notice"));
		assertFalse("Não deve conter erros.", result.included().containsKey("erros"));
	}
	
	@Test
	public void naoDeveriaAtualizarSenhaUsuarioException() {
		Usuario usuario = renanigtSenhaAlterada();
		String senhaConfirm = "321";
		String senhaAtual = "123";
		
		doThrow(new RuntimeException()).when(usuarioService).atualiza(usuario);
		when(usuarioService.pesquisarSenha(1)).thenReturn(renanigt().getSenha());
		
		usuarioController.atualizaSenha(usuario, senhaConfirm, senhaAtual);
		
		assertTrue("Deveria conter mensagem de erro.", result.included().containsKey("erros"));
		assertFalse("Não deveria conter mensagem de sucesso.", result.included().containsKey("notice"));
	}
	
	@Test(expected=ValidationException.class)
	public void naoDeveriaAtualizarSenhaUsuarioAtualDiferente() {
		Usuario usuario = renanigtSenhaAlterada();
		String senhaConfirm = "321";
		String senhaAtual = "124";
		
		when(usuarioService.pesquisarSenha(1)).thenReturn(renanigt().getSenha());
		
		usuarioController.atualizaSenha(usuario, senhaConfirm, senhaAtual);
	}

	@Test(expected=ValidationException.class)
	public void naoDeveriaAtualizarSenhaUsuarioConfirmDiferente() {
		Usuario usuario = renanigtSenhaAlterada();
		String senhaConfirm = "322";
		String senhaAtual = "123";
		
		when(usuarioService.pesquisarSenha(1)).thenReturn(renanigt().getSenha());
		
		usuarioController.atualizaSenha(usuario, senhaConfirm, senhaAtual);
	}
	
	private Usuario aquinofb() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("Felipe Aquino");
		usuario.setLogin("aquinofb");
		usuario.setSenha("321");
		
		return usuario;
	}

	private Usuario loginIgualAquinoFb() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("Fulano");
		usuario.setLogin("aquinofb");
		usuario.setSenha("123");
		
		return usuario;
	}
	
	private Usuario criaUsuarioVazio() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("");
		usuario.setLogin("");
		usuario.setSenha("");
		
		return usuario;
	}

	private Usuario renanigt() {
		Usuario usuario = new Usuario();
		
		usuario.setId(1);
		usuario.setNome("Renan Montenegro");
		usuario.setLogin("renanigt");
		usuario.setSenha("123");
		
		return usuario;
	}

	private Usuario renanigtSenhaAlterada() {
		Usuario usuario = new Usuario();
		
		usuario.setId(1);
		usuario.setNome("Renan Montenegro");
		usuario.setLogin("renanigt");
		usuario.setSenha("321");
		
		return usuario;
	}
	
}
