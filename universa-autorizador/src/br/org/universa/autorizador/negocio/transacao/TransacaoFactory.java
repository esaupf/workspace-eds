package br.org.universa.autorizador.negocio.transacao;

import java.io.IOException;
import java.util.Properties;

public class TransacaoFactory {

	private static TransacaoFactory instancia = null;

	private TransacaoFactory() {

	}

	public static TransacaoFactory get() {
		if (instancia == null) {
			instancia = new TransacaoFactory();
		}
		return instancia;
	}

	public AbstractTransacaoMediator cria(Transacao transacao) {
		AbstractTransacaoMediator mediator = null;
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream(
					"TipoDaTransacao.properties"));
			String tipoDaTransacao = transacao.getTipoDaTransacao().getChave()
					.toString();
			String classeConcreta = properties.getProperty(tipoDaTransacao);
			mediator = (AbstractTransacaoMediator) Class
					.forName(classeConcreta).newInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mediator;
	}

}
