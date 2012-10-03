package br.org.universa.webbank.apresentacao.web.conta;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ContextRelativeResource;

import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.webbank.servico.vo.ContaVO;
import br.org.universa.webbank.servico.vo.LancamentoDaContaVO;

public class ExtratoDaContaPage extends SaldoDaContaPage {

	private static final long serialVersionUID = 1L;

	public ExtratoDaContaPage(ContaVO contaVO) {
		super(contaVO);

		form.add(novaListaDeLancamentosDaConta());
	}

	@Override
	protected String getTitulo() {
		return "Extrato da Conta";
	}

	@SuppressWarnings("serial")
	private ListView<LancamentoDaContaVO> novaListaDeLancamentosDaConta() {
		ListView<LancamentoDaContaVO> lstLancamentosDaConta = new ListView<LancamentoDaContaVO>(
				"lstLancamentosDaConta",
				new LoadableDetachableModel<List<LancamentoDaContaVO>>() {
					@Override
					protected List<LancamentoDaContaVO> load() {
						return conta.getLancamentosDaConta();
					}
				}) {
			@Override
			protected void populateItem(ListItem<LancamentoDaContaVO> item) {
				listaDeLancamentosDaContaPopulateItem(item);
			}
		};

		return lstLancamentosDaConta;
	}

	private void listaDeLancamentosDaContaPopulateItem(
			ListItem<LancamentoDaContaVO> item) {
		LancamentoDaContaVO lancamentoDaConta = item.getModelObject();

		item.add(new Image("imgTipoDoLancamento", new ContextRelativeResource(
				getImagem(lancamentoDaConta))));
		item.add(new Label("lblDataDoLancamento", new Model<Date>(
				lancamentoDaConta.getDataDoLancamento())));
		item.add(new Label("lblDescricao", new Model<String>(lancamentoDaConta
				.getDescricao())));
		item.add(new Label("lblValor", new Model<BigDecimal>(lancamentoDaConta
				.getValor())));
		item.add(new Label("lblSaldo", new Model<BigDecimal>(BigDecimal.ZERO)));
	}

	private String getImagem(LancamentoDaContaVO lancamentoDaConta) {
		if (lancamentoDaConta.getTipoDoLancamento().equals(
				TipoDoLancamento.DEBITO)) {
			return "imagens/debito.gif";
		} else {
			return "imagens/credito.gif";
		}
	}
}
