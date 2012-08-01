package br.com.controledecontas.service;

import br.com.controledecontas.model.Usuario;

public interface UsuarioService {

	Usuario pesquisaPorId(Integer id);

	void salva(Usuario usuario);

}
