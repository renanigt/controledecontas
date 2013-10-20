package br.com.controledecontas.service;

import br.com.controledecontas.model.Usuario;

public interface UsuarioService {

	Usuario pesquisaPorId(Integer id);

	void salva(Usuario usuario);

	Usuario autentica(String username, String password);

	void atualiza(Usuario usuarioAtualizado);

	String pesquisarPassword(Integer id);

}
