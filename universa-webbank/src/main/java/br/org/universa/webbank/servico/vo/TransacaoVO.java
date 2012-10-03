package br.org.universa.webbank.servico.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;

@SuppressWarnings("serial")
public class TransacaoVO implements Serializable {

	private Date dataHoraTransacao;
	private TipoDoLancamento tipoDoLancamento;
	private String descricao;
	private Integer agencia;
	private Integer conta;
	private BigDecimal valor;
	private String idDaTransacao;
	
	public Date getDataHoraTransacao() {
		return dataHoraTransacao;
	}

	public void setDataHoraTransacao(Date dataHoraTransacao) {
		this.dataHoraTransacao = dataHoraTransacao;
	}
	
	public TipoDoLancamento getTipoDoLancamento() {
		return tipoDoLancamento;
	}

	public void setTipoDoLancamento(TipoDoLancamento tipoDoLancamento) {
		this.tipoDoLancamento = tipoDoLancamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getIdDaTransacao() {
		return idDaTransacao;
	}

	public void setIdDaTransacao(String idDaTransacao) {
		this.idDaTransacao = idDaTransacao;
	}
}
