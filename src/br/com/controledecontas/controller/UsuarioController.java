package br.com.controledecontas.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.service.UsuarioService;

@Resource
public class UsuarioController {

	private Result result;
	private UsuarioService usuarioService;
	private Validator validator;
	
	public UsuarioController(Result result, UsuarioService usuarioService, Validator validator) {
		this.result = result;
		this.usuarioService = usuarioService;
		this.validator = validator;
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
			result.include("notice", "Usuário criado com sucesso !");
		} catch(Exception e) {
			result.include("erros", e.getMessage());
		}
		
		result.forwardTo(IndexController.class).index();
	}
	
	public void validaCamposObrigatorios(final Usuario usuario) {
		validator.checking(new Validations() {{
			that(!usuario.getNome().isEmpty(), "Nome", "O campo nome não pode ser vazio.");
			that(!usuario.getUsername().isEmpty(), "Username", "O campo username não pode ser vazio.");
			that(!usuario.getPassword().isEmpty(), "Password", "O campo password não pode ser vazio.");
		}});

		validator.onErrorUse(Results.page()).of(IndexController.class).index();
	}
	
}
