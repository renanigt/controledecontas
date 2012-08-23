package br.com.controledecontas.controller;

import java.math.BigDecimal;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import br.com.controledecontas.model.Conta;
import br.com.controledecontas.model.UsuarioSession;
import br.com.controledecontas.service.ContaService;

@Resource
public class ContaController {

	private Result result;
	private ContaService contaService;
	private UsuarioSession usuarioSession;
	private Validator validator;
	
	public ContaController(Result result, ContaService contaService, UsuarioSession usuarioSession, Validator validator) {
		this.result = result;
		this.contaService = contaService;
		this.usuarioSession = usuarioSession;;
		this.validator = validator;
	}

	@Get
	@Path("/conta/novo")
	public void novo() {
		
	}

	@Post
	@Path("/conta/salvar")
	public void salvar(Conta conta) {
		if(usuarioSession.isLogado()) {
			conta.setUsuario(usuarioSession.getUsuario());
			
			validaCamposObrigatorios(conta);
			
			try {
				contaService.salva(conta);
				result.include("notice", "Conta salva com sucesso!");
			} catch(Exception e) {
				result.include("erros", e.getMessage());
			}
			
			result.redirectTo(IndexController.class).index();
			
		} else {
			result.include("erros", "Você não está logado.");
			result.redirectTo(LoginController.class).login();
		}
		
	}

	private void validaCamposObrigatorios(final Conta conta) {
		validator.checking(new Validations() {{
			that(!conta.getDescricao().trim().isEmpty(), "Descrição", "Campo não pode ser vazio.");
			that(conta.getValor().compareTo(new BigDecimal("0.00")) == 1, "Valor", "Campo não pode ser vazio");
			that(conta.getData() != null, "Valor", "Campo não pode ser vazio");
			that(conta.getTipoConta() != null, "Valor", "Campo não pode ser vazio");
		}});
		
		validator.onErrorUse(Results.page()).of(this.getClass()).novo();
	}
	
}
