package br.com.controledecontas.service;

import br.com.controledecontas.model.Usuario;

public interface UsuarioService {

	Usuario pesquisaPorId(Integer id);

	void salva(Usuario usuario);

	Usuario autentica(String login, String senha);

	void atualiza(Usuario usuarioAtualizado);

	String pesquisarSenha(Integer id);

	Usuario pesquisarPorLogin(String login);

}
