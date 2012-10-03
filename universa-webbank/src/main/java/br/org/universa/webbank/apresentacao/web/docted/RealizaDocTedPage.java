package br.org.universa.webbank.apresentacao.web.docted;

import java.math.BigDecimal;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import br.org.universa.webbank.apresentacao.web.conta.SaldoDaContaPage;
import br.org.universa.webbank.apresentacao.web.transacao.ComprovantePage;
import br.org.universa.webbank.servico.adapter.AutorizadorAdapter;
import br.org.universa.webbank.servico.vo.ComprovanteVO;
import br.org.universa.webbank.servico.vo.ContaVO;

@SuppressWarnings("serial")
public class RealizaDocTedPage extends SaldoDaContaPage {

	private TextField<Integer> edtBancoFavorecido;
	private TextField<Integer> edtAgenciaFavorecida;
	private TextField<Integer> edtContaFavorecida;
	private TextField<String> edtCpfDoTitularDaContaFavorecida;
	private TextField<BigDecimal> edtValor;

	public RealizaDocTedPage(ContaVO contaVO) {
		super(contaVO);

		form.add(novoCampoBancoFavorecido());
		form.add(novoCampoAgenciaFavorecida());
		form.add(novoCampoContaFavorecida());
		form.add(novoCampoCpfDoTitularDaContaFavorecida());
		form.add(novoCheckBoxMesmoTitular());
		form.add(novoCampoValor());
		form.add(novoBotaoRealizarDOCTED());
	}

	@Override
	protected String getTitulo() {
		return "Realiza DOC/TED entre Bancos";
	}

	private TextField<Integer> novoCampoBancoFavorecido() {
		edtBancoFavorecido = new TextField<Integer>("edtBancoFavorecido",
				new Model<Integer>());
		edtBancoFavorecido.setRequired(true);
		edtBancoFavorecido.setLabel(new Model<String>("Banco Favorecido"));

		return edtBancoFavorecido;
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

	private TextField<String> novoCampoCpfDoTitularDaContaFavorecida() {
		edtCpfDoTitularDaContaFavorecida = new TextField<String>(
				"edtCpfDoTitularDaContaFavorecida", new Model<String>());
		edtCpfDoTitularDaContaFavorecida.setRequired(true);
		edtCpfDoTitularDaContaFavorecida.setLabel(new Model<String>(
				"CPF Do Titular da Conta Favorecida"));

		return edtCpfDoTitularDaContaFavorecida;
	}

	private CheckBox novoCheckBoxMesmoTitular() {
		CheckBox chkMesmoTitular = new CheckBox("chkMesmoTitular",
				new Model<Boolean>()) {
			@Override
			protected void onSelectionChanged(Boolean newSelection) {
				if (newSelection.equals(Boolean.TRUE)) {
					edtCpfDoTitularDaContaFavorecida.setModelObject(conta
							.getCpfDoTitular());
				} else {
					edtCpfDoTitularDaContaFavorecida.setModelObject(null);
				}
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				return true;
			}
		};
		return chkMesmoTitular;
	}

	private Button novoBotaoRealizarDOCTED() {
		return new Button("btnRealizarDOCTED") {
			@Override
			public void onSubmit() {
				try {
					ComprovanteVO comprovante = AutorizadorAdapter
							.get()
							.realizaDocTed(
									conta,
									new BigDecimal(edtValor.getValue()),
									Integer.valueOf(edtBancoFavorecido
											.getValue()),
									Integer.valueOf(edtAgenciaFavorecida
											.getValue()),
									Integer.valueOf(edtContaFavorecida
											.getValue()),
									edtCpfDoTitularDaContaFavorecida.getValue());
					setResponsePage(new ComprovantePage(comprovante));
					info("DOC/TED entre bancos efetuado com sucesso.");
				} catch (NumberFormatException e) {
					error("O valores informados para a transferência devem conter apenas números.");
				} catch (Exception e) {
					error(e.getMessage());
				}
			}
		};
	}
}