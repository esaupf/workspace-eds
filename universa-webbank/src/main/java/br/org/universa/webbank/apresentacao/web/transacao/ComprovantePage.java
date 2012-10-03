package br.org.universa.webbank.apresentacao.web.transacao;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.webbank.apresentacao.web.app.WebBankPage;
import br.org.universa.webbank.servico.vo.ComprovanteVO;

@SuppressWarnings("serial")
public class ComprovantePage extends WebBankPage {

	protected ComprovanteVO comprovante;
	protected Form<Object> form;

	public ComprovantePage(ComprovanteVO comprovante) {
		super();

		this.comprovante = comprovante;

		form = new Form<Object>("form");
		add(form);

		form.add(novoLabelAgencia());
		form.add(novoLabelConta());
		form.add(novoLabelTipoDaTransacao());
		form.add(novoLabelValor());
		form.add(novoLabelDataHoraTransacao());
		form.add(novoLabelIdDaTransacao());
	}

	private Label novoLabelAgencia() {
		return new Label("lblAgencia", new Model<String>(
				UtilHelper.getAgenciaFormatada(comprovante.getAgencia())));
	}

	private Label novoLabelConta() {
		return new Label("lblConta", new Model<String>(
				UtilHelper.getContaFormatada(comprovante.getConta())));
	}

	private Label novoLabelTipoDaTransacao() {
		return new Label("lblTipoDaTransacao", new PropertyModel<String>(this,
				"comprovante.tipoDaTransacao"));
	}

	private Label novoLabelValor() {
		return new Label("lblValor", new Model<String>(
				UtilHelper.getValorFormatado(comprovante.getValor())));
	}

	private Label novoLabelDataHoraTransacao() {
		return new Label("lblDataHoraTransacao", new Model<String>(
				UtilHelper.getDataHoraFormatada(comprovante
						.getDataHoraTransacao())));
	}

	private Label novoLabelIdDaTransacao() {
		return new Label("lblIdDaTransacao", new PropertyModel<String>(this,
				"comprovante.idDaTransacao"));
	}
}
