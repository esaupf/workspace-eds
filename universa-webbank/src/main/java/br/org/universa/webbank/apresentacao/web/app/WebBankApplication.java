package br.org.universa.webbank.apresentacao.web.app;

import java.util.Locale;

import org.apache.wicket.protocol.http.WebApplication;

import br.org.universa.webbank.apresentacao.web.conta.DepositoEmContaPage;
import br.org.universa.webbank.apresentacao.web.conta.ExtratoDaContaPage;
import br.org.universa.webbank.apresentacao.web.conta.SaldoDaContaPage;
import br.org.universa.webbank.apresentacao.web.conta.SaqueEmContaPage;
import br.org.universa.webbank.apresentacao.web.conta.TransferenciaEntreContasPage;
import br.org.universa.webbank.apresentacao.web.docted.RealizaDocTedPage;
import br.org.universa.webbank.apresentacao.web.fundos.InvestimentoEmFundosPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see br.org.universa.webbank.apresentacao.app.Start#main(String[])
 */
public class WebBankApplication extends WebApplication {

	@Override
	public Class<WebBankPage> getHomePage() {
		return WebBankPage.class;
	}
	
	@Override
	public void init() {
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		Locale.setDefault(new Locale("PT", "BR"));

		super.init();

		mountPage("/saldoDaConta", SaldoDaContaPage.class);
		mountPage("/extratoDaConta", ExtratoDaContaPage.class);
		mountPage("/saqueEmConta", SaqueEmContaPage.class);
		mountPage("/depositoEmConta", DepositoEmContaPage.class);
		mountPage("/transferenciaEntreContas",
				TransferenciaEntreContasPage.class);
		mountPage("/investimentoEmFundos", InvestimentoEmFundosPage.class);
		mountPage("/realizaDOCTED", RealizaDocTedPage.class);
		//mountPage("/consultaLogDeTransacao", null);
	}
}
