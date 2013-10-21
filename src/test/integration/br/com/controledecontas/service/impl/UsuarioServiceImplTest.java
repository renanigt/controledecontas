package br.com.controledecontas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import base.dbunit.DbUnitManager;
import br.com.controledecontas.model.Usuario;
import br.com.controledecontas.service.UsuarioService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
 "file:./src/main/webapp/WEB-INF/config/spring/applicationContext.xml",
 "file:./src/main/webapp/WEB-INF/config/spring/applicationContext-persistence-test.xml"
})
@Transactional
public class UsuarioServiceImplTest {

	private static final String DATASET = "src/test/integration/base/dbunit/xml/UsuarioServiceImplTest.xml";
	
	private static final Integer ID_VALIDO = 9999;
	private static final Integer ID_INVALIDO = -1;
	
	@Autowired
	DbUnitManager dbUnitManager;
	@Autowired
	UsuarioService usuarioService;
	
	@Before
	public void setup() {
		dbUnitManager.cleanAndInsert(DATASET);
	}
	
	@Test
	public void deveriaPesquisarUsuarioPorId() {
		Usuario usuarioIdValido = usuarioService.pesquisaPorId(ID_VALIDO);
		Usuario usuarioIdInvalido = usuarioService.pesquisaPorId(ID_INVALIDO);
		
		assertNotNull("Usuário não deve ser nulo.", usuarioIdValido);
		assertNull("Usuário não deve ser nulo.", usuarioIdInvalido);
	}

	@Test
	public void deveriaSalvarUsuario() {
		Usuario usuario = criaUsuario();
		
		usuarioService.salva(usuario);
		
		Usuario usuarioSalvo = usuarioService.pesquisaPorId(usuario.getId());
		
		assertEquals("Nome", "Renan Montenegro", usuarioSalvo.getNome());
		assertEquals("Login", "montenegro", usuarioSalvo.getLogin());
		assertEquals("Senha", "123", usuarioSalvo.getSenha());
	}
	
	@Test
	public void deveriaValidarUsuario() {
		Usuario usuario = usuarioService.autentica("renanigt", "123");
		
		assertNotNull("Usuário não deve ser nulo.", usuario);
		assertEquals("Nome", "RENAN MONTENEGRO", usuario.getNome());
		assertEquals("Login", "RENANIGT", usuario.getLogin());
		assertEquals("Senha", "123", usuario.getSenha());
	}
	
	@Test
	public void deveriaRetornarNuloParaUsuarioNaoValido() {
		Usuario usuario = usuarioService.autentica("renanigt", "senhaInvalida");
		
		assertNull("Usuário deve ser nullo.", usuario);
	}

	@Test
	public void deveriaAtualizarUsuarioSemAlterarSaldo() {
		Usuario usuario = usuarioService.pesquisaPorId(ID_VALIDO);
		
		usuarioService.atualiza(usuarioAtualizado());
		
		assertEquals("Nome", "Renan Teixeira Lima Verde Montenegro", usuario.getNome());
		assertEquals("Login", "renanlima", usuario.getLogin());
		assertEquals("Senha", "321", usuario.getSenha());
		assertEquals("Saldo", new BigDecimal("50.00"), usuario.getSaldo());
	}
	
	@Test
	public void deveriaPesquisarSenhaDoUsuario() {
		String senha = usuarioService.pesquisarSenha(ID_VALIDO);
		
		assertEquals("Senha", "123", senha);
	}
	
	private Usuario criaUsuario() {
		Usuario usuario = new Usuario();
		
		usuario.setNome("Renan Montenegro");
		usuario.setLogin("montenegro");
		usuario.setSenha("123");
		
		return usuario;
	}
	
	private Usuario usuarioAtualizado() {
		Usuario usuario = new Usuario();
		usuario.setId(9999);
		usuario.setNome("Renan Teixeira Lima Verde Montenegro");
		usuario.setLogin("renanlima");
		usuario.setSenha("321");
		
		return usuario;
	}
}
