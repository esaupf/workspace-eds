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
public class SaqueEmContaPage extends SaldoDaContaPage {

	private TextField<BigDecimal> edtValor;

	public SaqueEmContaPage(ContaVO contaVO) {
		super(contaVO);

		form.add(novoCampoValor());
		form.add(novoBotaoSacar());
	}

	@Override
	protected String getTitulo() {
		return "Saque em Conta";
	}

	private TextField<BigDecimal> novoCampoValor() {
		edtValor = new TextField<BigDecimal>("edtValor",
				new Model<BigDecimal>());

		edtValor.setRequired(true);
		edtValor.setLabel(new Model<String>("Valor do Saque"));

		return edtValor;
	}

	private Button novoBotaoSacar() {
		return new Button("btnSacar") {
			@Override
			public void onSubmit() {
				try {
					ComprovanteVO comprovante = AutorizadorAdapter.get().saca(
							conta, new BigDecimal(edtValor.getValue()));
					setResponsePage(new ComprovantePage(comprovante));
					info("Saque efetuado com sucesso.");
				} catch (NumberFormatException e) {
					error("O valor do saque deve conter apenas números.");
				} catch (Exception e) {
					error(e.getMessage());
				}
			}
		};
	}
}
