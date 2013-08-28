package br.com.controledecontas.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.controledecontas.annotation.Public;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.model.UsuarioSession;
import br.com.controledecontas.service.UsuarioService;

@Public
@Resource
public class LoginController {

	private Result result;
	private UsuarioService usuarioService;
	private UsuarioSession usuarioSession;
	
	public LoginController(Result result, UsuarioService usuarioService, UsuarioSession usuarioLoginController) {
		this.result = result;
		this.usuarioService = usuarioService;
		this.usuarioSession = usuarioLoginController;
	}
	
	@Get
	@Path("/login")
	public void login() {
		
	}
	
	@Post
	@Path("/login/logar")
	public void logar(String username, String password) {
		Usuario usuario = usuarioService.autentica(username, password);
		
		if(usuario != null) {
			usuarioSession.setUsuario(usuario);
			result.redirectTo(ContaController.class).index();
		} else {
			result.include("erros", "Usuário ou senha inválidos.");
			result.redirectTo(this).login();
		}
		
	}
	
	@Get
	@Path("/login/logout")
	public void logout() {
		usuarioSession.logout();
		
		result.redirectTo(this).login();
	}

}
