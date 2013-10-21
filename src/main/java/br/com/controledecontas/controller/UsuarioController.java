package br.com.controledecontas.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.validator.Validations;
import br.com.controledecontas.annotation.Public;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.model.UsuarioSession;
import br.com.controledecontas.service.UsuarioService;

@Public
@Resource
public class UsuarioController {

	private Result result;
	private UsuarioService usuarioService;
	private Validator validator;
	private Localization localization;
	private UsuarioSession usuarioSession;
	
	public UsuarioController(Result result, UsuarioService usuarioService, 
			Validator validator, Localization localization, UsuarioSession usuarioSession) {
		this.result = result;
		this.usuarioService = usuarioService;
		this.validator = validator;
		this.localization = localization;
		this.usuarioSession = usuarioSession;
	}

	@Get
	@Path("/usuario/novo")
	public void novo() {

	}

	@Get
	@Path("/usuario/atualiza/{id}")
	public void edita(Integer id) {
		if(usuarioSession.isLogado()) {
			result.include("usuario", usuarioSession.getUsuario());
		} else {
			result.redirectTo(IndexController.class).index();
		}
	}
	
	@Post
	@Path("/usuario/novo/salvar")
	public void salva(Usuario usuario) {
		validaCamposObrigatorios(usuario);
		validator.onErrorForwardTo(this).novo();
		
		try {
			usuarioService.salva(usuario);
			result.include("notice", localization.getMessage("usuario.salvo.sucesso"));
			result.redirectTo(LoginController.class).login();
		} catch(Exception e) {
			result.include("erros", e.getMessage());
		}
		
	}
	
	@Post
	@Path("/usuario/atualizaPerfil/salvar")
	public void atualizaPerfil(Usuario usuario) {
		validaCamposObrigatorios(usuario);
		validator.onErrorForwardTo(this).edita(usuario.getId());
		
		try {
			usuarioService.atualiza(usuario);
			result.include("notice", localization.getMessage("usuario.atualizado.sucesso"));
			result.redirectTo(ContaController.class).index();
		} catch (Exception e) {
			result.include("erros", e);
			result.forwardTo(this).edita(usuario.getId());
		}
		
	}
	
	@Post
	@Path("/usuario/atualizaSenha/salvar")
	public void atualizaSenha(Usuario usuario, String senhaConfirm, String senhaAtual) {
		String senha = usuarioService.pesquisarSenha(usuario.getId());
		
		validaSenha(usuario, senhaConfirm);
		if(!senha.equals(senhaAtual)) {
			validator.add(new ValidationMessage("usuario.senha.invalida", "Senha Atual"));
		}
		validator.onErrorForwardTo(this).edita(usuario.getId());
		
		try {
			usuarioService.atualiza(usuario);
			result.include("notice", localization.getMessage("usuario.atualizado.sucesso"));
			result.redirectTo(ContaController.class).index();
		} catch (Exception e) {
			result.include("erros", e);
			result.forwardTo(this).edita(usuario.getId());
		}
	}

	private void validaCamposObrigatorios(final Usuario usuario) {
		validator.checking(new Validations() {{
			that(usuario.getNome() != null && !usuario.getNome().trim().isEmpty(), "Nome", "campo.nao.vazio");
			that(usuario.getLogin() != null && !usuario.getLogin().trim().isEmpty(), "Login", "campo.nao.vazio");
			that(usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty(), "Senha", "campo.nao.vazio");
		}});
	}
	
	private void validaSenha(final Usuario usuario, final String senhaConfirm) {
		validator.checking(new Validations() {{
			that(usuario.getSenha().equals(senhaConfirm), "Confirmar Senha", "usuario.senha.confima.invalido");
		}});
	}
	
}
