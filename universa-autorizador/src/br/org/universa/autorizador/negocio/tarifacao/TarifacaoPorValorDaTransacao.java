package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.transacao.Transacao;

public class TarifacaoPorValorDaTransacao implements Tarifacao {

	private Tarifacao proximaTarifacao;

	@Override
	public double calcula(Transacao transacao) {
		double valorTransacao = 3000.00;
		double zeroVirgulaUmPorCento = 0.001;
		double zeroVirgulaQuinzePorCento = 0.0015;

		Conta conta;
		try {
			conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
					transacao.getConta());
		} catch (Exception e) {
			return 0.0;
		}

		if (transacao.getValor() > valorTransacao) {
			if (conta.getTipoDaCestaDeServicos().equals(
					TipoDaCestaDeServicos.BASICA)) {
				return transacao.getValor() * zeroVirgulaQuinzePorCento;
			} else {
				return transacao.getValor() * zeroVirgulaUmPorCento;
			}
		} else {
			return proximaTarifacao.calcula(transacao);
		}
	}

	@Override
	public void setProximaTarifacao(Tarifacao proximaTarifacao) {
		this.proximaTarifacao = proximaTarifacao;
	}

}
