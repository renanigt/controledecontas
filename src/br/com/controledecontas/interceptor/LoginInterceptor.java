package br.com.controledecontas.interceptor;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.controledecontas.annotation.Public;
import br.com.controledecontas.controller.LoginController;
import br.com.controledecontas.model.UsuarioSession;

@Intercepts
public class LoginInterceptor implements Interceptor {

	private Result result;
	private UsuarioSession usuarioSession;
	
	public LoginInterceptor(Result result, UsuarioSession  usuarioSession) {
		this.result = result;
		this.usuarioSession = usuarioSession;
	}
	
	public boolean accepts(ResourceMethod method) {
		return !(method.getMethod().isAnnotationPresent(Public.class) || method.getResource().getType().isAnnotationPresent(Public.class));
	}

	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {
		if(usuarioSession.isLogado()) {
			stack.next(method, resourceInstance);
		} else {
			result.redirectTo(LoginController.class).login();
		}
	}

}
