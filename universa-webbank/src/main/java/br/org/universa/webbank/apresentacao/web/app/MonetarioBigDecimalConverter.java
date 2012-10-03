package br.org.universa.webbank.apresentacao.web.app;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractNumberConverter;

@SuppressWarnings("serial")
public class MonetarioBigDecimalConverter extends
		AbstractNumberConverter<BigDecimal> {
	Locale locale_pt_br = new Locale("PT", "BR");

	@Override
	protected Class<BigDecimal> getTargetType() {
		return BigDecimal.class;
	}

	@Override
	public NumberFormat getNumberFormat(Locale locale) {
		NumberFormat numberFormat = getNumberFormat(locale_pt_br);
		numberFormat.setMaximumFractionDigits(2);

		return numberFormat;
	}

	public BigDecimal convertToObject(String value, Locale locale) {
		value = value.replace(".", ",");

		return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}