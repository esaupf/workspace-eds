package br.org.universa.webbank.apresentacao.web.fundos;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import br.org.universa.autorizador.negocio.fundos.TipoDoFundo;
import br.org.universa.webbank.apresentacao.web.conta.SaldoDaContaPage;
import br.org.universa.webbank.apresentacao.web.transacao.ComprovantePage;
import br.org.universa.webbank.servico.adapter.AutorizadorAdapter;
import br.org.universa.webbank.servico.vo.ComprovanteVO;
import br.org.universa.webbank.servico.vo.ContaVO;

@SuppressWarnings("serial")
public class InvestimentoEmFundosPage extends SaldoDaContaPage {

	private TextField<BigDecimal> edtSaldoAInvestir;
	private TipoDoFundo tipoDoFundo;

	public InvestimentoEmFundosPage(ContaVO contaVO) {
		super(contaVO);

		form.add(novoComboTipoDoFundo());
		form.add(novoCampoSaldoAInvestir());
		form.add(novoBotaoInvestir());
	}

	@Override
	protected String getTitulo() {
		return "Investimentos na Conta";
	}

	private FormComponent<TipoDoFundo> novoComboTipoDoFundo() {
		FormComponent<TipoDoFundo> cmbTipoDoFundo = new DropDownChoice<TipoDoFundo>(
				"cmbTipoDoFundo", new PropertyModel<TipoDoFundo>(this,
						"tipoDoFundo"),
				new LoadableDetachableModel<java.util.List<TipoDoFundo>>() {

					@Override
					protected List<TipoDoFundo> load() {
						return Arrays.asList(TipoDoFundo.values());
					}
				}, new ChoiceRenderer<TipoDoFundo>("valor", "chave"));

		cmbTipoDoFundo.setRequired(true);
		cmbTipoDoFundo.setLabel(new Model<String>("Tipo do Fundo"));

		return cmbTipoDoFundo;
	}

	private TextField<BigDecimal> novoCampoSaldoAInvestir() {
		edtSaldoAInvestir = new TextField<BigDecimal>("edtSaldoAInvestir",
				new Model<BigDecimal>());

		edtSaldoAInvestir.setRequired(true);
		edtSaldoAInvestir.setLabel(new Model<String>("Saldo a Investir"));

		return edtSaldoAInvestir;
	}

	private Button novoBotaoInvestir() {
		return new Button("btnInvestir") {
			@Override
			public void onSubmit() {
				try {
					ComprovanteVO comprovante = AutorizadorAdapter
							.get()
							.investeEmFundo(
									conta,
									new BigDecimal(edtSaldoAInvestir.getValue()),
									tipoDoFundo);
					setResponsePage(new ComprovantePage(comprovante));
					info("Investimento em Fundo efetuado com sucesso.");
				} catch (NumberFormatException e) {
					error("O saldo a investir deve conter apenas números.");
				} catch (Exception e) {
					error(e.getMessage());
				}
			}
		};
	}
}
