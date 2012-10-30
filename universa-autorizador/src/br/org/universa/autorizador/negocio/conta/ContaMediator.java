package br.org.universa.autorizador.negocio.conta;

import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.persistencia.dao.ContaDAO;

public class ContaMediator {
	
	private static ContaMediator instancia = null;
	
	private  ContaMediator() {
		// TODO Auto-generated constructor stub
	}
	
	public static ContaMediator get(){
		if(instancia == null){
			instancia = new ContaMediator();
		}
		return instancia;
	}

	public Conta consultaConta(Integer agencia, Integer numero) throws Exception{
		Conta conta = ContaDAO.get().consulta(agencia, numero);
		
		if(conta == null){
			throw new Exception(Mensagens.NAO_EXISTE_CONTA_PARA_AGENCIA_NUMERO);
		}
		return conta;
	}

	public void atualiza(Conta conta) {
		ContaDAO.get().atualiza(conta);
	}
}
