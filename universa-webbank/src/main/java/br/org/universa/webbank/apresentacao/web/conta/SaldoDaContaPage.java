package br.org.universa.webbank.apresentacao.web.conta;

import java.math.BigDecimal;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.PropertyModel;

import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.webbank.servico.adapter.AutorizadorAdapter;
import br.org.universa.webbank.servico.vo.ContaVO;

@SuppressWarnings("serial")
public class SaldoDaContaPage extends ContaPage {

	protected ContaVO conta;

	public SaldoDaContaPage(ContaVO contaVO) {
		super();

		conta = contaVO;
		form.add(novoLabelAgencia());
		form.add(novoLabelConta());
		form.add(novoLabelCliente());
		form.add(novoLabelSaldoDisponivel());
	}

	@Override
	protected String getTitulo() {
		return "Saldo da Conta";
	}

	@Override
	protected Button novoBotaoConsultar() {
		Button btnConsultar = new Button("btnConsultar") {
			@Override
			public void onSubmit() {
				if (edtAgencia.getValue().isEmpty()
						|| edtConta.getValue().isEmpty()) {
					error("A agência e a conta devem ser informadas.");
				} else {
					try {
						conta = AutorizadorAdapter.get().consultaConta(
								Integer.valueOf(edtAgencia.getValue()),
								Integer.valueOf(edtConta.getValue()));
					} catch (NumberFormatException e) {
						error("A agência e a conta devem conter apenas números.");
					} catch (Exception e) {
						error(e.getMessage());
					}
				}
			}
		};

		btnConsultar.setDefaultFormProcessing(false);

		return btnConsultar;
	}

	protected ContaVO getConta() {
		return conta;
	}

	private Label novoLabelAgencia() {
		return new Label("lblAgencia", new PropertyModel<Integer>(this,
				"conta.agencia"));
	}

	private Label novoLabelConta() {
		return new Label("lblConta", new PropertyModel<Integer>(this,
				"conta.numero"));
	}

	private Label novoLabelCliente() {
		return new Label("lblCliente", new PropertyModel<String>(this,
				"conta.titular"));
	}

	private Label novoLabelSaldoDisponivel() {
		return new Label("lblSaldoDisponivel", new PropertyModel<BigDecimal>(
				this, "conta.saldo"));
	}

	public String getAgenciaFormatada(Integer agencia) {
		if (agencia != null) {
			return UtilHelper.getAgenciaFormatada(agencia);
		} else {
			return "";
		}
	}
}