package br.com.controledecontas.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.model.UsuarioSession;
import br.com.controledecontas.service.UsuarioService;

@Resource
public class LoginController {

	private Result result;
	private UsuarioService usuarioService;
	private UsuarioSession usuarioLoginController;
	
	public LoginController(Result result, UsuarioService usuarioService, UsuarioSession usuarioLoginController) {
		this.result = result;
		this.usuarioService = usuarioService;
		this.usuarioLoginController = usuarioLoginController;
	}
	
	@Get
	@Path("/login")
	public void login() {
		
	}
	
	@Post
	@Path("/login/logar")
	public void logar(String username, String password) {
		Usuario usuario = usuarioService.valida(username, password);
		
		if(usuario != null) {
			usuarioLoginController.setUsuario(usuario);
			result.redirectTo(IndexController.class).index();
		} else {
			result.include("erros", "Usuário ou senha inválidos.");
			result.redirectTo(this).login();
		}
		
	}

}
