package br.com.controledecontas.service;

import br.com.controledecontas.model.Conta;

public interface ContaService {

	Conta pesquisaPorId(Integer id);

	void salva(Conta conta);

}
