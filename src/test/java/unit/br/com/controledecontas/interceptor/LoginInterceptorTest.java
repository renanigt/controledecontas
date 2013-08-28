package br.com.controledecontas.interceptor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.DefaultResourceMethod;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.controledecontas.annotation.Public;
import br.com.controledecontas.controller.LoginController;
import br.com.controledecontas.model.UsuarioSession;

public class LoginInterceptorTest {

	private LoginInterceptor loginInterceptor;
	@Mock
	private Result result;
	@Mock
	private UsuarioSession usuarioSession;
	@Mock
	private InterceptorStack stack;
	@Mock
	private LoginController loginController;
	
	private ResourceMethod methodPublicAnnotationClass;
	private ResourceMethod noPublicAnnotationMethod;
	private ResourceMethod publicAnnotationMethod;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		loginInterceptor = new LoginInterceptor(result, usuarioSession);
		
		methodPublicAnnotationClass = DefaultResourceMethod.instanceFor(ResourcePublic.class, ResourcePublic.class.getMethod("methodPublicAnnotationClass"));
		noPublicAnnotationMethod = DefaultResourceMethod.instanceFor(Resource.class, Resource.class.getMethod("noPublicAnnotationMethod"));
		publicAnnotationMethod = DefaultResourceMethod.instanceFor(Resource.class, Resource.class.getMethod("publicAnnotationMethod"));
	}
	
	@Test
	public void shouldNotAcceptIfClassIsAnnotatedAsPublic() {
		assertFalse("Não deveria retornar verdadeiro.", loginInterceptor.accepts(methodPublicAnnotationClass));
	}
	
	@Test
	public void shouldAcceptIfMethodIsAnnotatedAsPublic() {
		assertTrue("Não deveria retornar falso", loginInterceptor.accepts(noPublicAnnotationMethod));
	}
	
	@Test
	public void shouldNotAcceptIfMethodIsNotAnnotatedAsPublic() {
		assertFalse("Não deveria retornar verdadeiro", loginInterceptor.accepts(publicAnnotationMethod));
	}
	
	@Test
	public void shouldRedirectIfUserIsNotLogged() {
		when(usuarioSession.isLogado()).thenReturn(false);
		when(result.redirectTo(LoginController.class)).thenReturn(loginController);
		
		loginInterceptor.intercept(stack, noPublicAnnotationMethod, loginController);
		
		verify(result, only()).redirectTo(LoginController.class);
		verify(stack, never()).next(noPublicAnnotationMethod, loginController);
	}
	
	@Test
	public void shouldProcessIfUserIsLogged() {
		when(usuarioSession.isLogado()).thenReturn(true);
		
		loginInterceptor.intercept(stack, noPublicAnnotationMethod, loginController);
		
		verify(result, never()).redirectTo(LoginController.class);
		verify(stack, only()).next(noPublicAnnotationMethod, loginController);
	}
	
	@Public
	class ResourcePublic {
		
		public void methodPublicAnnotationClass() {
			
		}
		
	}
	
	class Resource {
		
		public void noPublicAnnotationMethod() {
			
		}
		
		@Public
		public void publicAnnotationMethod() {
			
		}
		
	}
	
}
