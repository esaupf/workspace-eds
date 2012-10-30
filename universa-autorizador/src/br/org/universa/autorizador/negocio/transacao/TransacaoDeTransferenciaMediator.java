package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.LancamentoDaConta;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;

public class TransacaoDeTransferenciaMediator extends AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao)
			throws Exception {
		
		TransacaoDeTransferencia transacaoTransferencia = null;
		
		if (transacao instanceof TransacaoDeTransferencia) {
			transacaoTransferencia = (TransacaoDeTransferencia) transacao;
		}		
		
		Conta contaOrigem = ContaMediator.get().consultaConta(transacaoTransferencia.getAgencia(),
				transacaoTransferencia.getConta());

		Conta contaFavorecida = ContaMediator.get().consultaConta(transacaoTransferencia.getAgenciaFavorecida(),
				transacaoTransferencia.getContaFavorecida());
		
		contaOrigem.debita(transacaoTransferencia.getValor());
		contaFavorecida.credita(transacaoTransferencia.getValor());
	
		gerarLancamentoDebito(contaOrigem, transacaoTransferencia);
		gerarLancamentoCredito(contaFavorecida, transacaoTransferencia);
		
		ContaMediator.get().atualiza(contaOrigem);
		ContaMediator.get().atualiza(contaFavorecida);
	}
	
	private void gerarLancamentoDebito(Conta conta, Transacao transacao) {

        conta.adicionaLancamentoDaConta(new LancamentoDaConta(
                TipoDoLancamento.DEBITO, transacao.getTipoDaTransacao()
                        .getValor(), transacao.getValor()));
     }
	
	private void gerarLancamentoCredito(Conta conta, Transacao transacao) {

        conta.adicionaLancamentoDaConta(new LancamentoDaConta(
                TipoDoLancamento.CREDITO, transacao.getTipoDaTransacao()
                        .getValor(), transacao.getValor()));
     }
	

}
