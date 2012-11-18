package br.com.controledecontas.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.controledecontas.model.Conta;
import br.com.controledecontas.model.TipoConta;
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
	@Path("/conta")
	public void index() {
		Calendar dataAtual = Calendar.getInstance();
		List<Conta> contas = contaService.pesquisaPorMesEAno(usuarioSession.getUsuario(), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.YEAR));
		
		result.include("contas", contas);
	}
	
	@Get
	@Path("/conta/novo")
	public void novo() {
		result.include("tiposConta", Arrays.asList(TipoConta.values()));
	}

	@Post
	@Path("/conta/novo/salvar")
	public void salvar(Conta conta) {
		conta.setUsuario(usuarioSession.getUsuario());
		
		validaCamposObrigatorios(conta);
		
		try {
			contaService.salva(conta);
			result.include("notice", "Conta salva com sucesso!");
		} catch(Exception e) {
			result.include("erros", e.getMessage());
		}
		
		result.redirectTo(IndexController.class).index();
	}

	private void validaCamposObrigatorios(final Conta conta) {
		validator.checking(new Validations() {{
			that(conta.getDescricao() != null && !conta.getDescricao().trim().isEmpty(), "Descrição", "Campo não pode ser vazio.");
			that(conta.getValor() != null && conta.getValor().compareTo(new BigDecimal("0.00")) != 0, "Valor", "Campo não pode ser vazio");
			that(conta.getData() != null, "Data", "Campo não pode ser vazio");
			that(conta.getTipoConta() != null, "Tipo Conta", "Campo não pode ser vazio");
		}});
		
		validator.onErrorRedirectTo(this.getClass()).novo();
	}
	
}
