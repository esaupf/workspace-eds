package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.transacao.Transacao;

public class TarifacaoMediator {

	private static TarifacaoMediator instancia = null;

	private TarifacaoMediator() {
		// Construtor privado
	}

	public static TarifacaoMediator get() {
		if (instancia == null) {
			instancia = new TarifacaoMediator();
		}

		return instancia;
	}

	public double tarifa(Transacao transacao) {
		TarifacaoPorValorDaTransacao transacaoPorValorDaTransacao = new TarifacaoPorValorDaTransacao();
		TarifacaoPorCestaDeServicos tarifacaoPorCestaDeServicos = new TarifacaoPorCestaDeServicos();
		TarifacaoPorQuantidadeDeTransacoes tarifacaoPorQuantidadeDeTransacoes = new TarifacaoPorQuantidadeDeTransacoes();

		transacaoPorValorDaTransacao
				.setProximaTarifacao(tarifacaoPorCestaDeServicos);
		transacaoPorValorDaTransacao
				.setProximaTarifacao(tarifacaoPorQuantidadeDeTransacoes);
		tarifacaoPorQuantidadeDeTransacoes
				.setProximaTarifacao(new SemTarifacao());

		return transacaoPorValorDaTransacao.calcula(transacao);
	}
}
