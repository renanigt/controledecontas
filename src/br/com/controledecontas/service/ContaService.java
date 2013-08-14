package br.com.controledecontas.service;

import java.util.List;

import br.com.controledecontas.model.Conta;
import br.com.controledecontas.model.TipoConta;
import br.com.controledecontas.model.Usuario;

public interface ContaService {

	Conta pesquisaPorId(Integer id);

	void salva(Conta conta);

	void deleta(Conta conta);

	void atualiza(Conta conta, Conta contaAnterior);

	List<Conta> pesquisaPorTipo(Usuario usuario, TipoConta tipoConta);

	List<Conta> pesquisaPorMesEAno(Usuario usuario, Integer mes, Integer ano);

	List<Conta> pesquisaPorDescricao(Usuario usuario, String descricao);

}
