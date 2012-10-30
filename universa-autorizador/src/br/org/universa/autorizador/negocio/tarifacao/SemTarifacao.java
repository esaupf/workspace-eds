package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.transacao.Transacao;

public class SemTarifacao implements Tarifacao {



	@Override
	public double calcula(Transacao transacao) {
		return 0;
	}

	@Override
	public void setProximaTarifacao(Tarifacao proximaTarifacao) {
		

	}

}
