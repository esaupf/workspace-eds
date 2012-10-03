package br.org.universa.webbank.apresentacao.web.conta;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import br.org.universa.webbank.apresentacao.web.app.WebBankPage;

@SuppressWarnings("serial")
public abstract class ContaPage extends WebBankPage {

	protected Form<Object> form;
	protected TextField<Integer> edtAgencia;
	protected TextField<Integer> edtConta;

	public ContaPage() {
		super();

		form = new Form<Object>("form");
		add(form);

		form.add(novoLabelTitulo());
		form.add(novoCampoAgencia());
		form.add(novoCampoConta());
		form.add(novoBotaoConsultar());
	}

	protected Label novoLabelTitulo() {
		return new Label("lblTitulo", getTitulo());
	}

	protected abstract String getTitulo();

	protected TextField<Integer> novoCampoAgencia() {
		edtAgencia = new TextField<Integer>("edtAgencia", new Model<Integer>());

		return edtAgencia;
	}

	protected TextField<Integer> novoCampoConta() {
		edtConta = new TextField<Integer>("edtConta", new Model<Integer>());

		return edtConta;
	}

	protected abstract Button novoBotaoConsultar();
}
