package br.org.universa.autorizador.negocio.docted;

import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.transacao.TransacaoDeDocTed;


public class DocTedBuilder {

	private static DocTedBuilder instancia = null;
	
	private DocTedBuilder() {
		// construtor privado
	}

	public static DocTedBuilder get() {
		if (instancia == null) {
			instancia = new DocTedBuilder();
		}

		return instancia;
	}
	
	
	public DocTed constroi(TransacaoDeDocTed transacaoDeDocTed) throws Exception{
		
		DocTed ted = new DocTed();
				
		ted.setIdentificadorDaTransacao(transacaoDeDocTed.getIdentificadorDaTransacao());
		ted.setBancoDeOrigem(UtilHelper.getBancoUniversa());
		ted.setAgenciaDeOrigem(transacaoDeDocTed.getAgencia());
		ted.setContaDeOrigem(transacaoDeDocTed.getConta());
		
		Conta conta = ContaMediator.get().consultaConta(transacaoDeDocTed.getAgencia(), transacaoDeDocTed.getConta());
		
		ted.setCpfDoTitularDaContaDeOrigem(conta.getTitular().getCpf());
		ted.setValor(transacaoDeDocTed.getValor());
		ted.setBancoFavorecido(transacaoDeDocTed.getBancoFavorecido());
		ted.setAgenciaFavorecida(transacaoDeDocTed.getAgenciaFavorecida());
		ted.setContaFavorecida(transacaoDeDocTed.getContaFavorecida());
		ted.setCpfDoTitularDaContaFavorecida(transacaoDeDocTed.getCpfDoTitularDaContaFavorecida());
				
		if(transacaoDeDocTed.getValor() > 3000){
			ted.setCategoriaDaTransferencia(CategoriaDaTransferencia.DOC);
		}else{
			ted.setCategoriaDaTransferencia(CategoriaDaTransferencia.TED);
		}
		
		if(ted.getCpfDoTitularDaContaDeOrigem().equals(ted.getCpfDoTitularDaContaFavorecida())){
			ted.setTipoDoDocTed(TipoDoDocTed.C);
		}else{
			ted.setTipoDoDocTed(TipoDoDocTed.D);
		}
		
		ArquivoDeDOCHelper.get().registraDOC(ted);
		
		return ted;
		
	}
	
	
}
