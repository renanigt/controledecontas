package br.com.controledecontas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
		
		assertNotNull("Conta não deve ser nula", contaIdExistente);
		assertNull("Conta deve ser nula", contaIdInexistente);
	}
	
	@Test
	public void deveriaSalvarConta() {
		Conta conta = criaConta();
		
		contaService.salva(conta);
		
		Conta contaSalva = contaService.pesquisaPorId(conta.getId());
		
		verificaDadosConta(contaSalva, new GregorianCalendar(2012, Calendar.JULY, 25).getTime(), "Supermercado", TipoConta.DEBITO, new BigDecimal("13.43"), ID_VALIDO_USUARIO);
	}
	
	@Test
	public void deveriaRemoverConta() {
		Conta conta = contaService.pesquisaPorId(ID_VALIDO_CONTA);
		
		contaService.deleta(conta);
		
		Conta contaRemovida = contaService.pesquisaPorId(ID_VALIDO_CONTA);
		
		assertNull("Conta deve ser nula", contaRemovida);
	}
	
	@Test
	public void deveriaAtualizarConta() {
		Conta conta = contaService.pesquisaPorId(ID_VALIDO_CONTA);
		
		GregorianCalendar dataConta = new GregorianCalendar(2012, Calendar.JULY, 28);
		
		conta.setDescricao("Recebido pagamento.");
		conta.setTipoConta(TipoConta.CREDITO);
		conta.setData(dataConta.getTime());
		conta.setValor(new BigDecimal("30.00"));
		
		contaService.atualiza(conta);
		
		Conta contaAtualizada = contaService.pesquisaPorId(ID_VALIDO_CONTA);
		
		verificaDadosConta(contaAtualizada, dataConta.getTime(), "Recebido pagamento.", TipoConta.CREDITO, new BigDecimal("30.00"), ID_VALIDO_USUARIO);
	}

	@Test
	public void deveriaPesquisarContasPorTipo() {
		List<Conta> contas = contaService.pesquisaPorTipo(usuarioService.pesquisaPorId(ID_VALIDO_USUARIO), TipoConta.CREDITO);
		
		GregorianCalendar dataConta = new GregorianCalendar(2012, Calendar.AUGUST, 01);
		
		assertEquals("Quantidade de contas", 1, contas.size());
		
		verificaDadosConta(contas.get(0), dataConta.getTime(), "SALÁRIO", TipoConta.CREDITO, new BigDecimal("800.30"), ID_VALIDO_USUARIO);
	}
	
	@Test
	public void naoDeveriaPesquisarPorTipoParaUsuarioNulo() {
		List<Conta> contas = contaService.pesquisaPorTipo(null, TipoConta.DEBITO);
		
		assertTrue("Deveria retornar uma lista vazia.", contas.isEmpty());
	}
	
	@Test
	public void deveriaPesquisarContasPorMesEAno() {
		List<Conta> contas = contaService.pesquisaPorMesEAno(usuarioService.pesquisaPorId(ID_VALIDO_USUARIO), Calendar.AUGUST, 2012);
		
		assertEquals("Quantidade de contas", 2, contas.size());
		
		verificaDadosConta(contas.get(0), new GregorianCalendar(2012, Calendar.AUGUST, 1).getTime(), "SALÁRIO", TipoConta.CREDITO, new BigDecimal("800.30"), ID_VALIDO_USUARIO);
		verificaDadosConta(contas.get(1), new GregorianCalendar(2012, Calendar.AUGUST, 2).getTime(), "CONTA CELULAR", TipoConta.DEBITO, new BigDecimal("60.30"), ID_VALIDO_USUARIO);
	}
	
	@Test
	public void naoDeveriaPesquisarContasPorMesParaUsuarioNulo() {
		List<Conta> contas = contaService.pesquisaPorMesEAno(null, Calendar.AUGUST, 2012);
		
		assertTrue("Deveria retornar uma lista vazia.", contas.isEmpty());
	}
	
	@Test
	public void deveriaPesquisarContaPorDescricao() {
		List<Conta> contas = contaService.pesquisaPorDescricao(usuarioService.pesquisaPorId(ID_VALIDO_USUARIO), "CONTA");
		
		assertEquals("Quantidade de contas", 1, contas.size());
		
		verificaDadosConta(contas.get(0), new GregorianCalendar(2012, Calendar.AUGUST, 2).getTime(), "CONTA CELULAR", TipoConta.DEBITO, new BigDecimal("60.30"), ID_VALIDO_USUARIO);
	}
	
	@Test
	public void naoDeveriaPesquisarContaPorDescricaoParaUsuarioNulo() {
		List<Conta> contas = contaService.pesquisaPorDescricao(null, "CONTA");
		
		assertTrue("Deveria retornar uma lista vazia.", contas.isEmpty());
	}
	
	private void verificaDadosConta(Conta conta, Date data, String descricao, TipoConta tipoDeConta, BigDecimal valor, Integer idUsuario) {
		assertEquals("Data", dateFormat.format(data), dateFormat.format(conta.getData()));
		assertEquals("Descrição", descricao, conta.getDescricao());
		assertEquals("Tipo de Conta", tipoDeConta, conta.getTipoConta());
		assertEquals("Valor", valor, conta.getValor());
		assertEquals("ID Usuário", idUsuario, conta.getUsuario().getId());
	}
	
	private Conta criaConta() {
		Conta conta = new Conta();
		GregorianCalendar data = new GregorianCalendar(2012, Calendar.JULY, 25);
		
		conta.setData(data.getTime());
		conta.setDescricao("Supermercado");
		conta.setTipoConta(TipoConta.DEBITO);
		conta.setValor(new BigDecimal("13.43"));
		conta.setUsuario(usuarioService.pesquisaPorId(ID_VALIDO_USUARIO));
		
		return conta;
	}

}
