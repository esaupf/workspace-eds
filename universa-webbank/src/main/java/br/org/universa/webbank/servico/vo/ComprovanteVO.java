package br.org.universa.webbank.servico.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class ComprovanteVO implements Serializable {

	private String tipoDaTransacao;
	private Integer agencia;
	private Integer conta;
	private BigDecimal valor;
	private Date dataHoraTransacao;
	private String idDaTransacao;

	public String getTipoDaTransacao() {
		return tipoDaTransacao;
	}

	public void setTipoDaTransacao(String tipoDaTransacao) {
		this.tipoDaTransacao = tipoDaTransacao;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataHoraTransacao() {
		return dataHoraTransacao;
	}

	public void setDataHoraTransacao(Date dataHoraTransacao) {
		this.dataHoraTransacao = dataHoraTransacao;
	}

	public String getIdDaTransacao() {
		return idDaTransacao;
	}

	public void setIdDaTransacao(String idDaTransacao) {
		this.idDaTransacao = idDaTransacao;
	}
}
