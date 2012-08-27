package br.com.controledecontas.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.controledecontas.annotation.Public;

@Public
@Resource
public class IndexController {

	private Result result;
	
	public IndexController(Result result) {
		this.result = result;
	}
	
	@Path("/")
	public void index() {
		result.include("funcionando", "VRaptor está funcionando !");
	}
	
}
