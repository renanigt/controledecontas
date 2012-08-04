package br.com.controledecontas.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class IndexController {

	Result result;
	
	public IndexController(Result result) {
		this.result = result;
	}
	
	@Path("/")
	public void index() {
		result.include("funcionando", "VRaptor está funcionando !");
	}
	
}
