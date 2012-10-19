package br.com.controledecontas.model;

public enum TipoConta {

	CREDITO("Crédito"), DEBITO("Débito");
	
	String descricao;

	private TipoConta(String descricao) {
		this.descricao = descricao;
	}

	public String getName() {
		return name();
	}
	
	public String getDescricao() {
		return descricao;
	}

}
