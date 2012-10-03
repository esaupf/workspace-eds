package br.org.universa.webbank.apresentacao.web.app;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

@SuppressWarnings("serial")
public class TextFieldMonetario<T> extends TextField<T> {

	public TextFieldMonetario(String id) {
		super(id);
	}

	public TextFieldMonetario(String id, IModel<T> model) {
		super(id, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <C> IConverter<C> getConverter(Class<C> type) {
		return (IConverter<C>) new MonetarioBigDecimalConverter();
	}
}