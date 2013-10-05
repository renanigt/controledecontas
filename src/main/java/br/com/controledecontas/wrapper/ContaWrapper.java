package br.com.controledecontas.wrapper;

import java.math.BigDecimal;

import br.com.controledecontas.model.Conta;

public class ContaWrapper {

	
	private BigDecimal saldo;
	
	private String mensagem;
	
	public ContaWrapper(Conta conta, String mensagem) {
		this.saldo = conta.getUsuario().getSaldo();
		this.mensagem = mensagem;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
