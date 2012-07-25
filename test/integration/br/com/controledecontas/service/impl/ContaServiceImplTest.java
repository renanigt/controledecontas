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
import br.com.controdecontas.service.ContaService;
import br.com.controledecontas.model.Conta;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
 "file:./WebContent/WEB-INF/config/spring/applicationContext.xml",
 "file:./WebContent/WEB-INF/config/spring/applicationContext-persistence-test.xml"
})
@Transactional
public class ContaServiceImplTest {

	private static final String DATASET = "test/integration/base/dbunit/xml/ContaServiceImplTest.xml";
	
	private static final Integer ID_VALIDO = 9999;
	private static final Integer ID_INVALIDO = -1;
	
	@Autowired
	DbUnitManager dbUnitManager;
	@Autowired
	ContaService contaService;
	
	@Before
	public void setup() {
		dbUnitManager.cleanAndInsert(DATASET);
	}

	@Test
	public void deveriaBuscarContaPorId() {
		Conta contaIdExistente = contaService.buscarPorId(ID_VALIDO);
		Conta contaIdInexistente = contaService.buscarPorId(ID_INVALIDO);
		
		assertNotNull("Conta existente", contaIdExistente);
		assertNull("Conta inexistente", contaIdInexistente);
		
	}
	
}
