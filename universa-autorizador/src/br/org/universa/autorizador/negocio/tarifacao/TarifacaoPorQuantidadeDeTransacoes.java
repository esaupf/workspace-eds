package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.transacao.Transacao;
import br.org.universa.autorizador.negocio.transacao.TransacaoMediator;

public class TarifacaoPorQuantidadeDeTransacoes implements Tarifacao {

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

		int quantidadeTransacao = TransacaoMediator.get()
				.consultaQuantidadeDeTransacoesDaContaNoDia(conta);

		if (conta.getTipoDaCestaDeServicos().equals(
				TipoDaCestaDeServicos.BASICA)
				&& quantidadeTransacao > 2) {
			return 2.00;
		} else if (conta.getTipoDaCestaDeServicos().equals(
				TipoDaCestaDeServicos.ESPECIAL)
				&& quantidadeTransacao > 4) {
			return 1.5;
		} else {
			return proximaTarifacao.calcula(transacao);
		}
	}

	@Override
	public void setProximaTarifacao(Tarifacao proximaTarifacao) {
		this.proximaTarifacao = proximaTarifacao;
	}

}
