package br.com.controledecontas.wrapper;

import java.math.BigDecimal;

public class ContaWrapper {

	private BigDecimal saldo;
	
	private String mensagem;

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
