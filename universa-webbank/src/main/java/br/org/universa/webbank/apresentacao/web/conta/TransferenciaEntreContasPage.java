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
public class TransferenciaEntreContasPage extends SaldoDaContaPage {

	private TextField<Integer> edtAgenciaFavorecida;
	private TextField<Integer> edtContaFavorecida;
	private TextField<BigDecimal> edtValor;

	public TransferenciaEntreContasPage(ContaVO contaVO) {
		super(contaVO);

		form.add(novoCampoAgenciaFavorecida());
		form.add(novoCampoContaFavorecida());
		form.add(novoCampoValor());
		form.add(novoBotaoTransferir());
	}

	@Override
	protected String getTitulo() {
		return "Transferêcia entre Contas";
	}

	private TextField<Integer> novoCampoAgenciaFavorecida() {
		edtAgenciaFavorecida = new TextField<Integer>("edtAgenciaFavorecida",
				new Model<Integer>());

		edtAgenciaFavorecida.setRequired(true);
		edtAgenciaFavorecida.setLabel(new Model<String>("Agência Favorecida"));

		return edtAgenciaFavorecida;
	}

	private TextField<Integer> novoCampoContaFavorecida() {
		edtContaFavorecida = new TextField<Integer>("edtContaFavorecida",
				new Model<Integer>());

		edtContaFavorecida.setRequired(true);
		edtContaFavorecida.setLabel(new Model<String>("Conta Favorecida"));

		return edtContaFavorecida;
	}

	private TextField<BigDecimal> novoCampoValor() {
		edtValor = new TextField<BigDecimal>("edtValor",
				new Model<BigDecimal>());

		edtValor.setRequired(true);
		edtValor.setLabel(new Model<String>("Valor a Transferir"));

		return edtValor;
	}

	private Button novoBotaoTransferir() {
		return new Button("btnTransferir") {
			@Override
			public void onSubmit() {
				try {
					ComprovanteVO comprovante = AutorizadorAdapter.get()
							.transfere(
									conta,
									new BigDecimal(edtValor.getValue()),
									Integer.valueOf(edtAgenciaFavorecida
											.getValue()),
									Integer.valueOf(edtContaFavorecida
											.getValue()));
					setResponsePage(new ComprovantePage(comprovante));
					info("Transferência entre contas efetuada com sucesso.");
				} catch (NumberFormatException e) {
					error("O valores informados para a transferência devem conter apenas números.");
				} catch (Exception e) {
					error(e.getMessage());
				}
			}
		};
	}
}