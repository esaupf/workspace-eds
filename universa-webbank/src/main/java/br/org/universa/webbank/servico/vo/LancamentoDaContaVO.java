package br.org.universa.webbank.servico.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;

@SuppressWarnings("serial")
public class LancamentoDaContaVO implements Serializable {

	private Date dataDoLancamento;
	private TipoDoLancamento tipoDoLancamento;
	private String descricao;
	private BigDecimal valor;

	public LancamentoDaContaVO(Date dataDoLancamento,
			TipoDoLancamento tipoDoLancamento, String descricao,
			BigDecimal valor) {
		this.dataDoLancamento = dataDoLancamento;
		this.tipoDoLancamento = tipoDoLancamento;
		this.descricao = descricao;
		this.valor = valor;
	}

	public Date getDataDoLancamento() {
		return dataDoLancamento;
	}

	public void setDataDoLancamento(Date dataDoLancamento) {
		this.dataDoLancamento = dataDoLancamento;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
