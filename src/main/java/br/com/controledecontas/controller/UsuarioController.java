package br.com.controledecontas.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.validator.Validations;
import br.com.controledecontas.annotation.Public;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.service.UsuarioService;

@Public
@Resource
public class UsuarioController {

	private Result result;
	private UsuarioService usuarioService;
	private Validator validator;
	private Localization localization;
	
	public UsuarioController(Result result, UsuarioService usuarioService, 
			Validator validator, Localization localization) {
		this.result = result;
		this.usuarioService = usuarioService;
		this.validator = validator;
		this.localization = localization;
	}

	@Get
	@Path("/usuario/novo")
	public void novo() {

	}

	@Post
	@Path("/usuario/novo/salvar")
	public void salva(Usuario usuario) {
		validaCamposObrigatorios(usuario);
		
		try {
			usuarioService.salva(usuario);
			result.include("notice", localization.getMessage("usuario.salvo.sucesso"));
			result.redirectTo(LoginController.class).login();
		} catch(Exception e) {
			result.include("erros", e.getMessage());
		}
		
	}
	
	private void validaCamposObrigatorios(final Usuario usuario) {
		validator.checking(new Validations() {{
			that(usuario.getNome() != null && !usuario.getNome().trim().isEmpty(), "Nome", "campo.nao.vazio");
			that(usuario.getUsername() != null && !usuario.getUsername().trim().isEmpty(), "Username", "campo.nao.vazio");
			that(usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty(), "Password", "campo.nao.vazio");
		}});

		validator.onErrorForwardTo(this).novo();
	}
	
}
