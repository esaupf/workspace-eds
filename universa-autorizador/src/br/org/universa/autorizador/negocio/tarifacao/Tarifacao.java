package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.transacao.Transacao;

public interface Tarifacao {
	
	public double calcula (Transacao transacao);
	public void setProximaTarifacao (Tarifacao tarifacao);

}
