package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.LancamentoDaConta;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;

public class TransacaoDeSaqueMediator extends AbstractTransacaoMediator{

	@Override
	protected void executaRegrasEspecificas(Transacao transacao)
			throws Exception {
		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
				transacao.getConta());

		conta.debita(transacao.getValor());
			
		ContaMediator.get().atualiza(conta);
	}
	
	
	
}
