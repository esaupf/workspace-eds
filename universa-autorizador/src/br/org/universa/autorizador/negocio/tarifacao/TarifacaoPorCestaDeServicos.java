package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.cestadeservicos.CestaDeServicosHelper;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.transacao.Transacao;

public class TarifacaoPorCestaDeServicos implements Tarifacao {

	private Tarifacao proximaTarifacao;

	@Override
	public double calcula(Transacao transacao) {

		Conta conta;
		try {
			conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
					transacao.getConta());
		} catch (Exception e) {
			return 0.0;
		}

		if (CestaDeServicosHelper.get().isTransacaoPassivelDeTarifacao(conta,
				transacao)) {
			if (CestaDeServicosHelper.get().isExcedeQuantidadeContratada(conta,
					transacao)) {
				return CestaDeServicosHelper.get().consultaTarifaExcedente(
						conta, transacao);
			} else {
				return proximaTarifacao.calcula(transacao);
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
