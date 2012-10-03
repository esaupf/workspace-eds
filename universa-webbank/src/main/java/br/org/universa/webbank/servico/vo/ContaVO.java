package br.org.universa.webbank.servico.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ContaVO implements Serializable {

	private Integer agencia;
	private Integer numero;
	private String titular;
	private String cpfDoTitular;
	private BigDecimal saldo = BigDecimal.ZERO;
	private List<LancamentoDaContaVO> lancamentosDaConta = new ArrayList<LancamentoDaContaVO>();

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getCpfDoTitular() {
		return cpfDoTitular;
	}

	public void setCpfDoTitular(String cpfDoTitular) {
		this.cpfDoTitular = cpfDoTitular;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal valor) {
		this.saldo = valor;
	}
	
	public void adicionaLancamentoDaConta(LancamentoDaContaVO lancamentoDaConta) {
		this.lancamentosDaConta.add(lancamentoDaConta);
	}

	public List<LancamentoDaContaVO> getLancamentosDaConta() {
		return lancamentosDaConta;
	}
}
