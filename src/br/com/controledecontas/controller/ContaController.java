package br.com.controledecontas.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.model.UsuarioSession;
import br.com.controledecontas.service.ContaService;

@Resource
public class ContaController {

	private Result result;
	private ContaService service;
	private UsuarioSession usuarioSession;
	private Validator validator;
	
	public ContaController(Result result, ContaService contaService, UsuarioSession usuarioSession, Validator validator) {
		this.result = result;
		this.service = contaService;
		this.usuarioSession = usuarioSession;
		this.validator = validator;
	}

	@Get
	@Path("/conta")
	public void index() {
		Integer mes = Calendar.getInstance().get(Calendar.MONTH);
		Integer ano = Calendar.getInstance().get(Calendar.YEAR);
		
		List<Conta> contas = service.pesquisaPorMesEAno(usuarioSession.getUsuario(), mes, ano);
		
		result.include("contas", contas);
	}

	@Get
	@Path("/conta/lista")
	public void lista() {
	}

	
	@Get
	@Path("/conta/novo")
	public void novo() {
		result.include("tiposConta", Arrays.asList(TipoConta.values()));
	}
	
	@Get
	@Path("/conta/atualiza/{id}")
	public void edita(Integer id) {
		Conta conta = service.pesquisaPorId(id);
		
		result.include("conta", conta);
		result.include("tiposConta", Arrays.asList(TipoConta.values()));
	}

	@Post
	@Path("/conta/novo/salvar")
	public void salvar(Conta conta) {
		validaCamposObrigatorios(conta);
		validator.onErrorForwardTo(this).novo();
		
		Usuario usuario = usuarioSession.getUsuario();
		conta.setUsuario(usuario);
		
		try {
			service.salva(conta);
			result.include("notice", "Conta salva com sucesso!");
		} catch(Exception e) {
			usuario.voltaAoSaldoAnterior();
			result.include("erro", e.getMessage());
		}
		
		result.redirectTo(IndexController.class).index();
	}

	@Post
	@Path("/conta/atualiza/salvar")
	public void atualizar(Conta conta) {
		validaCamposObrigatorios(conta);
		validator.onErrorForwardTo(this).edita(conta.getId());
		
		conta.setUsuario(usuarioSession.getUsuario());
		
		try {
			Conta contaAnterior = service.pesquisaPorId(conta.getId());
			
			service.atualiza(conta, contaAnterior);
			result.include("notice", "Conta atualizada com sucesso!");
			result.redirectTo(IndexController.class).index();
		} catch(Exception e) {
			result.include("erro", e.getMessage());
			result.forwardTo(this).edita(conta.getId());
		}
	}
	
	@Get
	@Path("/conta/deleta/{id}")
	public void deletar(Integer id) {
		Conta conta = service.pesquisaPorId(id);
		
		Usuario usuario = usuarioSession.getUsuario();
		conta.setUsuario(usuario);
		
		try {
			service.deleta(conta);
			result.include("notice", "Conta removida com sucesso!");
		} catch(Exception e) {
			result.include("erro", e.getMessage());
		}
		
		result.redirectTo(this).index();
	}
	
	@Get
	@Path("/conta/pesquisaPorPeriodo")
	public void pesquisaPorPeriodo(Date dataInicio, Date dataFim) {
		List<Conta> contas = service.pesquisarPorPeriodo(usuarioSession.getUsuario(), dataInicio, dataFim);
		
		result.include("contas", contas);
	}
	
	private void validaCamposObrigatorios(final Conta conta) {
		validator.checking(new Validations() {{
			that(conta.getDescricao() != null && !conta.getDescricao().trim().isEmpty(), "Descrição", "Campo não pode ser vazio.");
			that(conta.getValor() != null && conta.getValor().compareTo(new BigDecimal("0.00")) != 0, "Valor", "Campo não pode ser vazio");
			that(conta.getData() != null, "Data", "Campo não pode ser vazio");
			that(conta.getTipoConta() != null, "Tipo Conta", "Campo não pode ser vazio");
		}});
	}

}
