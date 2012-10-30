package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.autorizador.negocio.autorizacao.EstadoDaAutorizacao;
import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.LancamentoDaConta;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.autorizador.negocio.tarifacao.TarifacaoMediator;

public abstract class AbstractTransacaoMediator {

	public Autorizacao executa(Transacao transacao) {
		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setEstado(EstadoDaAutorizacao.AUTORIZADA);

		try {
			// Ir� executar v�rias coisas comuns
			validaDadosDaTransacao(transacao);
			executaRegrasEspecificas(transacao);
			verificarSeHaTarifacao(transacao);
			TransacaoMediator.get().insereTransacaoDaConta(transacao);
			
		} catch (Exception e) {
			autorizacao.setEstado(EstadoDaAutorizacao.NEGADA);
			autorizacao.setMotivoDaNegacao(e.getMessage());
		}

		return autorizacao;
	}

	protected void validaDadosDaTransacao(Transacao transacao) throws Exception {
		if (!transacao.validaDados()) {
			throw new Exception(
					Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO);
		}
	}

	protected abstract void executaRegrasEspecificas(Transacao transacao)
			throws Exception;

	private void verificarSeHaTarifacao(Transacao transacao) throws Exception {

		double tarifa = TarifacaoMediator.get().tarifa(transacao);

		if (tarifa > 0) {

			Conta conta = ContaMediator.get().consultaConta(
					transacao.getAgencia(), transacao.getConta());

			conta.debita(tarifa);

			gerarLancamento(conta, transacao);
			ContaMediator.get().atualiza(conta);

		}

	}

	private void gerarLancamento(Conta conta, Transacao transacao) {

		TipoDoLancamento tipoDoLancamento = TipoDoLancamento.DEBITO;
		String descricao = transacao.getTipoDaTransacao().getValor();
		double valor = transacao.getValor();
		LancamentoDaConta lancamentoDaConta = new LancamentoDaConta(
				tipoDoLancamento, descricao, valor);
		conta.adicionaLancamentoDaConta(lancamentoDaConta);
	}

}
