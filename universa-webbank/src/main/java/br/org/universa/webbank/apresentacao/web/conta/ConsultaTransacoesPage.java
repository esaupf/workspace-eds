package br.org.universa.webbank.apresentacao.web.conta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ContextRelativeResource;

import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.webbank.apresentacao.web.app.WebBankPage;
import br.org.universa.webbank.servico.adapter.AutorizadorAdapter;
import br.org.universa.webbank.servico.vo.TransacaoVO;

@SuppressWarnings("serial")
public class ConsultaTransacoesPage extends WebBankPage {

	protected Form<Object> form;
	protected TextField<Date> edtDataDeReferencia;
	private List<TransacaoVO> transacoes = new ArrayList<TransacaoVO>();

	public ConsultaTransacoesPage() {
		super();

		form = new Form<Object>("form");
		add(form);

		form.add(novoCampoDataDeReferencia());
		form.add(novoBotaoConsultar());
		form.add(novaListaDeTransacoes());
	}

	private TextField<Date> novoCampoDataDeReferencia() {
		edtDataDeReferencia = new TextField<Date>("edtDataDeReferencia",
				new Model<Date>(new Date())) {
			@Override
			protected void onModelChanged() {
				if (edtDataDeReferencia.getValue().isEmpty()) {
					edtDataDeReferencia.setModelObject(new Date());
				}
			}
		};

		return edtDataDeReferencia;
	}

	private Button novoBotaoConsultar() {
		Button btnConsultar = new Button("btnConsultar") {
			@Override
			public void onSubmit() {
				if (edtDataDeReferencia.getValue().isEmpty()) {
					error("A data de referência deve ser informada.");
				} else {
					try {
						transacoes = AutorizadorAdapter.get()
								.consultaTransacoes(
										UtilHelper.getData(edtDataDeReferencia
												.getValue()));
					} catch (Exception e) {
						error(e.getMessage());
					}
				}
			}
		};

		btnConsultar.setDefaultFormProcessing(false);

		return btnConsultar;
	}

	private ListView<TransacaoVO> novaListaDeTransacoes() {
		ListView<TransacaoVO> lstTransacoes = new ListView<TransacaoVO>(
				"lstTransacoes",
				new LoadableDetachableModel<List<TransacaoVO>>() {
					@Override
					protected List<TransacaoVO> load() {
						return transacoes;
					}
				}) {
			@Override
			protected void populateItem(ListItem<TransacaoVO> item) {
				listaDeTransacoesPopulateItem(item);
			}
		};

		return lstTransacoes;
	}

	private void listaDeTransacoesPopulateItem(ListItem<TransacaoVO> item) {
		TransacaoVO transacao = item.getModelObject();

		item.add(new Label("lblDataHoraTransacao", new Model<String>(UtilHelper
				.getDataHoraFormatada(transacao.getDataHoraTransacao()))));
		item.add(new Image("imgTipoDoLancamento", new ContextRelativeResource(
				getImagem(transacao))));
		item.add(new Label("lblDescricao", new Model<String>(transacao
				.getDescricao())));
		item.add(new Label("lblAgencia", new Model<String>(UtilHelper
				.getAgenciaFormatada(transacao.getAgencia()))));
		item.add(new Label("lblConta", new Model<String>(UtilHelper
				.getContaFormatada(transacao.getConta()))));
		item.add(new Label("lblValor", new Model<String>(UtilHelper
				.getValorFormatado(transacao.getValor()))));
		item.add(new Label("lblIdDaTransacao", new Model<String>(transacao
				.getIdDaTransacao())));
	}

	private String getImagem(TransacaoVO transacao) {
		if (transacao.getTipoDoLancamento().equals(TipoDoLancamento.DEBITO)) {
			return "imagens/debito.gif";
		} else {
			return "imagens/credito.gif";
		}
	}
}
