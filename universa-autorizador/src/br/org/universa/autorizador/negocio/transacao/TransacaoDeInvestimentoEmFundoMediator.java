package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.LancamentoDaConta;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.autorizador.negocio.fundos.FundoDeInvestimentoMediator;

public class TransacaoDeInvestimentoEmFundoMediator extends
		AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao)
			throws Exception {
		TransacaoDeInvestimentoEmFundo transacaoDeInvestimentoEmFundo = (TransacaoDeInvestimentoEmFundo) transacao;

		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
				transacao.getConta());

		conta.debita(transacao.getValor());

		double rentabilidadeLiquida = FundoDeInvestimentoMediator.get()
				.calculaRentabilidade(
						transacaoDeInvestimentoEmFundo.getTipoDoFundo(),
						transacao.getValor());

		conta.credita(transacao.getValor() + rentabilidadeLiquida);

		gerarLancamento(transacaoDeInvestimentoEmFundo, conta,
				rentabilidadeLiquida);

		// gera lançamento na conta
		ContaMediator.get().atualiza(conta);

	}

	private void gerarLancamento(TransacaoDeInvestimentoEmFundo transacao,
			Conta conta, double rentabilidade) {

		LancamentoDaConta lancamento = new LancamentoDaConta(
				TipoDoLancamento.CREDITO, transacao.getTipoDaTransacao()
						.getValor()
						+ " "
						+ transacao.getTipoDoFundo().getValor(), rentabilidade);
		
		conta.adicionaLancamentoDaConta(lancamento);

	}

}
