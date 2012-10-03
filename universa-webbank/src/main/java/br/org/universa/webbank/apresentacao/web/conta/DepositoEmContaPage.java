package br.org.universa.webbank.apresentacao.web.conta;

import java.math.BigDecimal;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import br.org.universa.webbank.apresentacao.web.transacao.ComprovantePage;
import br.org.universa.webbank.servico.adapter.AutorizadorAdapter;
import br.org.universa.webbank.servico.vo.ComprovanteVO;
import br.org.universa.webbank.servico.vo.ContaVO;

@SuppressWarnings("serial")
public class DepositoEmContaPage extends SaldoDaContaPage {

	private TextField<BigDecimal> edtValor;

	public DepositoEmContaPage(ContaVO contaVO) {
		super(contaVO);

		form.add(novoCampoValor());
		form.add(novoBotaoDepositar());
	}

	@Override
	protected String getTitulo() {
		return "Depósito em Conta";
	}

	private TextField<BigDecimal> novoCampoValor() {
		edtValor = new TextField<BigDecimal>("edtValor",
				new Model<BigDecimal>());

		edtValor.setRequired(true);
		edtValor.setLabel(new Model<String>("Valor do Depósito"));

		return edtValor;
	}

	private Button novoBotaoDepositar() {
		return new Button("btnDepositar") {
			@Override
			public void onSubmit() {
				try {
					ComprovanteVO comprovante = AutorizadorAdapter.get()
							.deposita(conta,
									new BigDecimal(edtValor.getValue()));
					setResponsePage(new ComprovantePage(comprovante));
					info("Depósito efetuado com sucesso.");
				} catch (NumberFormatException e) {
					error("O valor do depósito deve conter apenas números.");
				} catch (Exception e) {
					error(e.getMessage());
				}
			}
		};
	}
}