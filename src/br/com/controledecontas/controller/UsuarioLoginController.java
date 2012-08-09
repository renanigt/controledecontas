package br.com.controledecontas.controller;

import java.io.Serializable;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.controledecontas.model.Usuario;

@SessionScoped
@Component
public class UsuarioLoginController implements Serializable {

	private static final long serialVersionUID = -4121350912525357709L;
	
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLogado() {
		return usuario != null;
	}

	public void logout() {
		usuario = null;
	}
	
}
