package br.com.controledecontas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 2214404601933626559L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="NOME", nullable=false)
	private String nome;
	
	@Column(name="USERNAME", unique=true, nullable=false)
	private String login;
	
	@Column(name="PASSWORD", nullable=false)
	private String senha;
	
	@OneToMany(mappedBy="usuario", fetch=FetchType.LAZY)
	private List<Conta> contas;
	
	@Column(name="SALDO")
	private BigDecimal saldo = new BigDecimal("0.00");
	
	@Transient
	private BigDecimal saldoAnterior;

	public Usuario(String nome, String login, String senha, BigDecimal saldo) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.saldo = saldo;
	}
	
	public Usuario() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void alteraSaldo(Conta conta, Boolean isDelete) {
		this.saldoAnterior = this.saldo;
		
		if(!isDelete) {
			if (conta.getTipoConta() == TipoConta.CREDITO) {
				this.saldo = this.saldo.add(conta.getValor());
			} else {
				this.saldo = this.saldo.subtract(conta.getValor());
			}
		} else {
			if (conta.getTipoConta() == TipoConta.DEBITO) {
				this.saldo = this.saldo.add(conta.getValor());
			} else {
				this.saldo = this.saldo.subtract(conta.getValor());
			}
		}
	}

	public void voltaAoSaldoAnterior() {
		this.saldo = this.saldoAnterior;
	}
	
}
