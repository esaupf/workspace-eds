package br.org.universa.webbank.apresentacao.web.app;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import br.org.universa.webbank.apresentacao.web.conta.ConsultaTransacoesPage;
import br.org.universa.webbank.apresentacao.web.conta.DepositoEmContaPage;
import br.org.universa.webbank.apresentacao.web.conta.ExtratoDaContaPage;
import br.org.universa.webbank.apresentacao.web.conta.SaldoDaContaPage;
import br.org.universa.webbank.apresentacao.web.conta.SaqueEmContaPage;
import br.org.universa.webbank.apresentacao.web.conta.TransferenciaEntreContasPage;
import br.org.universa.webbank.apresentacao.web.docted.RealizaDocTedPage;
import br.org.universa.webbank.apresentacao.web.fundos.InvestimentoEmFundosPage;
import br.org.universa.webbank.servico.vo.ContaVO;

@SuppressWarnings("serial")
public class WebBankPage extends WebPage {
	private static final String CSS_MSG_INFO = "msgInfo";
	private static final String CSS_MSG_ERRO = "msgErro";
	private static final String NOME_DA_APLICACAO = "Universa WebBank";

	public WebBankPage() {
		super();

		add(new Label("lblNomeDaAplicacao", NOME_DA_APLICACAO));
		add(novoLinkSaldoDaConta());
		add(novoLinkExtratoDaConta());
		add(novoLinkSaqueEmConta());
		add(novoLinkDepositoEmConta());
		add(novoLinkTransferenciaEntreContas());
		add(novoLinkInvestimentoEmFundos());
		add(novoLinkRealizaDocTed());
		add(novoLinkConsultaTransacoes());
		add(novoPainelDeFeedback());
	}

	private Link<WebBankPage> novoLinkSaldoDaConta() {
		return new Link<WebBankPage>("lnkSaldoDaConta") {
			public void onClick() {
				setResponsePage(new SaldoDaContaPage(new ContaVO()));
			}
		};
	}

	private Link<WebBankPage> novoLinkExtratoDaConta() {
		return new Link<WebBankPage>("lnkExtratoDaConta") {
			public void onClick() {
				setResponsePage(new ExtratoDaContaPage(new ContaVO()));
			}
		};
	}

	private Link<WebBankPage> novoLinkSaqueEmConta() {
		return new Link<WebBankPage>("lnkSaqueEmConta") {
			public void onClick() {
				setResponsePage(new SaqueEmContaPage(new ContaVO()));
			}
		};
	}

	private Link<WebBankPage> novoLinkDepositoEmConta() {
		return new Link<WebBankPage>("lnkDepositoEmConta") {
			public void onClick() {
				setResponsePage(new DepositoEmContaPage(new ContaVO()));
			}
		};
	}

	private Link<WebBankPage> novoLinkTransferenciaEntreContas() {
		return new Link<WebBankPage>("lnkTransferenciaEntreContas") {
			public void onClick() {
				setResponsePage(new TransferenciaEntreContasPage(new ContaVO()));
			}
		};
	}

	private Link<WebBankPage> novoLinkInvestimentoEmFundos() {
		return new Link<WebBankPage>("lnkInvestimentoEmFundos") {
			public void onClick() {
				setResponsePage(new InvestimentoEmFundosPage(new ContaVO()));
			}
		};
	}

	private Link<WebBankPage> novoLinkRealizaDocTed() {
		return new Link<WebBankPage>("lnkRealizaDocTed") {
			public void onClick() {
				setResponsePage(new RealizaDocTedPage(new ContaVO()));
			}
		};
	}

	private Link<WebBankPage> novoLinkConsultaTransacoes() {
		return new Link<WebBankPage>("lnkConsultaTransacoes") {
			public void onClick() {
				setResponsePage(new ConsultaTransacoesPage());
			}
		};
	}

	protected FeedbackPanel novoPainelDeFeedback() {
		return new FeedbackPanel("fbmMensagem") {
			@Override
			protected String getCSSClass(FeedbackMessage message) {
				if (message.isError()) {
					return CSS_MSG_ERRO;
				} else if (message.isInfo()) {
					return CSS_MSG_INFO;
				} else {
					return super.getCSSClass(message);
				}
			}
		};
	}
}