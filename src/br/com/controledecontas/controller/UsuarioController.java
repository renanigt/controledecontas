package br.com.controledecontas.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.controledecontas.service.UsuarioService;

@Resource
public class UsuarioController {

	private Result result;
	private UsuarioService usuarioService;
	
	public UsuarioController(Result result, UsuarioService usuarioService) {
		this.result = result;
		this.usuarioService = usuarioService;
	}

	@Get
	@Path("/usuario/novo")
	public void novo() {

	}

}
