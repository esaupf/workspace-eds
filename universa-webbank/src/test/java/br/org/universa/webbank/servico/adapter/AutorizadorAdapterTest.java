package br.org.universa.webbank.servico.adapter;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.org.universa.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.autorizador.negocio.comum.EntradaDeConta;
import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.autorizador.negocio.fundos.TipoDoFundo;
import br.org.universa.autorizador.negocio.transacao.TipoDaTransacao;
import br.org.universa.webbank.servico.vo.ContaVO;

public class AutorizadorAdapterTest {

	@Before
	public void populaConta() {
		EntradaDeConta.get().insere(3, 3, "Penelope Cruz", "77276469115",
				1890.00, TipoDaCestaDeServicos.ESPECIAL);

		EntradaDeConta.get().insere(4, 4, "Nicole Kidman", "02728494430", 0.0,
				TipoDaCestaDeServicos.BASICA);
	}

	@Test
	public void testSaqueEmContaSemSaldoSuficiente() {
		try {
			ContaVO contaVO = AutorizadorAdapter.get().consultaConta(3, 3);
			AutorizadorAdapter.get().saca(contaVO, new BigDecimal("1891.00"));

			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(Mensagens.SALDO_INSUFICIENTE, e.getMessage());
		}
	}

	@Test
	public void testSaqueEmContaComSaldoSuficiente() {
		try {
			ContaVO contaVO = AutorizadorAdapter.get().consultaConta(3, 3);
			AutorizadorAdapter.get().saca(contaVO, new BigDecimal("90.00"));

			contaVO = AutorizadorAdapter.get().consultaConta(3, 3);
			Assert.assertEquals(new BigDecimal("1800"), contaVO.getSaldo());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testLancamentoEmContaParaSaque() {
		try {
			ContaVO contaVO = AutorizadorAdapter.get().consultaConta(3, 3);
			AutorizadorAdapter.get().saca(contaVO, new BigDecimal("90.00"));

			contaVO = AutorizadorAdapter.get().consultaConta(3, 3);
			if (!contaVO.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(new BigDecimal("90"), contaVO
						.getLancamentosDaConta().get(0).getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, contaVO
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(TipoDaTransacao.SAQUE_EM_CONTA.getValor(),
						contaVO.getLancamentosDaConta().get(0).getDescricao());
			} else {
				Assert.fail();
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testDepositoEmConta() {
		try {
			ContaVO contaVO = AutorizadorAdapter.get().consultaConta(3, 3);
			AutorizadorAdapter.get().deposita(contaVO, new BigDecimal("99.99"));

			contaVO = AutorizadorAdapter.get().consultaConta(3, 3);
			Assert.assertEquals(new BigDecimal("1989.99"), contaVO.getSaldo()
					.setScale(2, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testTransferenciaEntreContas() {
		try {
			ContaVO contaDeDebito = AutorizadorAdapter.get()
					.consultaConta(3, 3);
			ContaVO contaFavorecida = AutorizadorAdapter.get().consultaConta(4,
					4);

			AutorizadorAdapter.get().transfere(contaDeDebito,
					new BigDecimal("99.99"), contaFavorecida.getAgencia(),
					contaFavorecida.getAgencia());

			contaDeDebito = AutorizadorAdapter.get().consultaConta(3, 3);
			Assert.assertEquals(new BigDecimal("1790.01"), contaDeDebito
					.getSaldo().setScale(2, BigDecimal.ROUND_HALF_UP));

			contaFavorecida = AutorizadorAdapter.get().consultaConta(4, 4);
			Assert.assertEquals(new BigDecimal("99.99"), contaFavorecida
					.getSaldo().setScale(2, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testLancamentosEmContasParaTransferenciaEntreContas() {
		try {
			ContaVO contaDeDebito = AutorizadorAdapter.get()
					.consultaConta(3, 3);
			ContaVO contaFavorecida = AutorizadorAdapter.get().consultaConta(4,
					4);
			AutorizadorAdapter.get().transfere(contaDeDebito,
					new BigDecimal("90.00"), contaFavorecida.getAgencia(),
					contaFavorecida.getNumero());

			contaDeDebito = AutorizadorAdapter.get().consultaConta(3, 3);
			if (contaDeDebito.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(new BigDecimal("90"), contaDeDebito
						.getLancamentosDaConta().get(0).getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, contaDeDebito
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS.getValor(),
						contaDeDebito.getLancamentosDaConta().get(0)
								.getDescricao());
			}

			contaFavorecida = AutorizadorAdapter.get().consultaConta(4, 4);
			if (contaFavorecida.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(new BigDecimal("90"), contaFavorecida
						.getLancamentosDaConta().get(0).getValor());
				Assert.assertEquals(TipoDoLancamento.CREDITO, contaFavorecida
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS.getValor(),
						contaFavorecida.getLancamentosDaConta().get(0)
								.getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testInvesteEmFundo() {
		try {
			ContaVO conta = AutorizadorAdapter.get().consultaConta(3, 3);
			AutorizadorAdapter.get().investeEmFundo(conta, conta.getSaldo(),
					TipoDoFundo.CONSERVADOR);

			conta = AutorizadorAdapter.get().consultaConta(3, 3);
			Assert.assertEquals(new BigDecimal("1901.34"), conta.getSaldo()
					.setScale(2, BigDecimal.ROUND_HALF_UP));

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(
						conta.getSaldo().subtract(new BigDecimal("1890.00"))
								.setScale(2, BigDecimal.ROUND_HALF_UP), conta
								.getLancamentosDaConta().get(0).getValor()
								.setScale(2, BigDecimal.ROUND_HALF_UP));
				Assert.assertEquals(TipoDoLancamento.CREDITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.INVESTIMENTO_EM_FUNDO.getValor() + " "
								+ TipoDoFundo.CONSERVADOR.getValor(), conta
								.getLancamentosDaConta().get(0).getDescricao());
			}

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testRealizaDOC() {
		try {
			ContaVO conta = AutorizadorAdapter.get().consultaConta(3, 3);
			AutorizadorAdapter.get()
					.realizaDocTed(conta, new BigDecimal("890.00"), 444, 5555,
							6666666, "77276469115");

			conta = AutorizadorAdapter.get().consultaConta(3, 3);
			Assert.assertEquals(new BigDecimal("1000"), conta.getSaldo());

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(new BigDecimal("890"), conta
						.getLancamentosDaConta().get(0).getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals("DOC-C para a conta 444:5555:6666666",
						conta.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}