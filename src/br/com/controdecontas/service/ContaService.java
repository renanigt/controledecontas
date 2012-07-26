package br.com.controdecontas.service;

import br.com.controledecontas.model.Conta;

public interface ContaService {

	Conta pesquisaPorId(Integer id);

	void salva(Conta conta);

}
