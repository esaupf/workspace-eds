package br.org.universa.autorizador.servico;

import br.org.universa.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.transacao.AbstractTransacaoMediator;
import br.org.universa.autorizador.negocio.transacao.Transacao;
import br.org.universa.autorizador.negocio.transacao.TransacaoFactory;


public class AutorizadorFacade {
	
	private static AutorizadorFacade instancia = null;
	
	private AutorizadorFacade(){
		
	}
	
	public static AutorizadorFacade get(){
		if(instancia == null){
			instancia = new AutorizadorFacade();
		}
		return instancia;
	}

	public Conta consultaConta(Integer agencia, Integer numero) throws Exception {
		
		return ContaMediator.get().consultaConta(agencia, numero);
	}
	
	public Autorizacao executa(Transacao transacao) {
		AbstractTransacaoMediator mediator = TransacaoFactory.get().cria(transacao);
		return mediator.executa(transacao);
		
	}
}
