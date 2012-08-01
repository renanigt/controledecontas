package br.com.controledecontas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import base.dbunit.DbUnitManager;
import br.com.controledecontas.model.Conta;
import br.com.controledecontas.model.TipoConta;
import br.com.controledecontas.service.ContaService;
import br.com.controledecontas.service.UsuarioService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
 "file:./WebContent/WEB-INF/config/spring/applicationContext.xml",
 "file:./WebContent/WEB-INF/config/spring/applicationContext-persistence-test.xml"
})
@Transactional
public class ContaServiceImplTest {

	private static final String DATASET = "test/integration/base/dbunit/xml/ContaServiceImplTest.xml";
	
	private static final Integer ID_VALIDO_CONTA = 9999;
	private static final Integer ID_INVALIDO_CONTA = -1;
	
	private static final Integer ID_VALIDO_USUARIO = 9998;
	
	@Autowired
	DbUnitManager dbUnitManager;
	@Autowired
	ContaService contaService;
	@Autowired
	UsuarioService usuarioService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Before
	public void setup() {
		dbUnitManager.cleanAndInsert(DATASET);
	}

	@Test
	public void deveriaPesquisarContaPorId() {
		Conta contaIdExistente = contaService.pesquisaPorId(ID_VALIDO_CONTA);
		Conta contaIdInexistente = contaService.pesquisaPorId(ID_INVALIDO_CONTA);
		
		assertNotNull("Conta existente", contaIdExistente);
		assertNull("Conta inexistente", contaIdInexistente);
	}
	
	@Test
	public void deveriaSalvarConta() {
		Conta conta = criaConta();
		
		contaService.salva(conta);
		
		Conta contaSalva = contaService.pesquisaPorId(conta.getId());
		
		GregorianCalendar dataConta = new GregorianCalendar(2012, Calendar.JULY, 25);
		
		verificaDadosConta(contaSalva, dataConta.getTime(), "Supermercado", TipoConta.Debito, new BigDecimal("13.43"), ID_VALIDO_USUARIO);
	}
	
	public void verificaDadosConta(Conta conta, Date data, String descricao, TipoConta tipoDeConta, BigDecimal valor, Integer idUsuario) {
		assertEquals("Data", dateFormat.format(data), dateFormat.format(conta.getData()));
		assertEquals("Descrição", descricao, conta.getDescricao());
		assertEquals("Tipo de Conta", tipoDeConta, conta.getTipoConta());
		assertEquals("Valor", valor, conta.getValor());
		assertEquals("ID Usuário", idUsuario, conta.getUsuario().getId());
	}
	
	public Conta criaConta() {
		Conta conta = new Conta();
		GregorianCalendar data = new GregorianCalendar(2012, Calendar.JULY, 25);
		
		conta.setData(data.getTime());
		conta.setDescricao("Supermercado");
		conta.setTipoConta(TipoConta.Debito);
		conta.setValor(new BigDecimal("13.43"));
		conta.setUsuario(usuarioService.pesquisaPorId(ID_VALIDO_USUARIO));
		
		return conta;
	}

}
