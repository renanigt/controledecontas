package br.com.controledecontas.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import base.dbunit.DbUnitManager;
import br.com.controdecontas.service.UsuarioService;
import br.com.controledecontas.model.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
 "file:./WebContent/WEB-INF/config/spring/applicationContext.xml",
 "file:./WebContent/WEB-INF/config/spring/applicationContext-persistence-test.xml"
})
@Transactional
public class UsuarioServiceImplTest {

	private static final String DATASET = "test/integration/base/dbunit/xml/UsuarioServiceImplTest.xml";
	
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
	
}
