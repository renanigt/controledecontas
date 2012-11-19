package br.com.controledecontas.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {

	Usuario usuario;
	
	@Before
	public void setUp() {
		usuario = new Usuario("Renan", "renanigt", "123", new BigDecimal("20.00"));
	}
	
	@Test
	public void deveriaAumentarValorDoSaldo() {
		usuario.alteraSaldo(credito());
		
		assertEquals("Saldo", new BigDecimal("30.00"), usuario.getSaldo());
	}
	
	@Test
	public void deveriaDiminuirValorDoSaldo() {
		usuario.alteraSaldo(debito());
		
		assertEquals("Saldo", new BigDecimal("9.50"), usuario.getSaldo());
	}

	@Test
	public void deveriaVoltarAoSaldoAnterior() {
		usuario.alteraSaldo(credito());
		
		usuario.voltaAoSaldoAnterior();
		
		assertEquals("Saldo", new BigDecimal("20.00"), usuario.getSaldo());
	}
	
	public Conta debito() {
		Conta conta = new Conta();
		
		conta.setTipoConta(TipoConta.DEBITO);
		conta.setValor(new BigDecimal("10.50"));
		
		return conta;
	}
	
	public Conta credito() {
		Conta conta = new Conta();
		
		conta.setTipoConta(TipoConta.CREDITO);
		conta.setValor(new BigDecimal("10.00"));
		
		return conta;
	}
}
