package br.org.universa.webbank.servico.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.fundos.TipoDoFundo;
import br.org.universa.autorizador.negocio.transacao.Transacao;
import br.org.universa.autorizador.servico.AutorizadorFacade;
import br.org.universa.webbank.servico.vo.ComprovanteVO;
import br.org.universa.webbank.servico.vo.ContaVO;
import br.org.universa.webbank.servico.vo.TransacaoVO;

public class AutorizadorAdapter {

	private static AutorizadorAdapter instancia = null;

	private AutorizadorAdapter() {
		// Construtor privado
	}

	public static AutorizadorAdapter get() {
		if (instancia == null) {
			instancia = new AutorizadorAdapter();
		}

		return instancia;
	}

	public ContaVO consultaConta(Integer agencia, Integer numero)
			throws Exception {
		Conta conta = AutorizadorFacade.get().consultaConta(agencia, numero);
		
		ContaVO contaVO = new ContaVO();
		contaVO.setAgencia(conta.getAgencia());
		contaVO.setCpfDoTitular(conta.getTitular().getCpf());
		contaVO.setNumero(conta.getNumero());
		contaVO.setSaldo(new BigDecimal(conta.getSaldo()));
		contaVO.setTitular(conta.getTitular().getNome());
		
		return contaVO;
	}

	public ComprovanteVO deposita(ContaVO conta, BigDecimal valor)
			throws Exception {
		// TODO - Implementar
		return null;
	}

	public ComprovanteVO saca(ContaVO conta, BigDecimal valor) throws Exception {
		// TODO - Implementar
		return null;
	}

	public ComprovanteVO transfere(ContaVO conta, BigDecimal valor,
			Integer agenciaFavorecida, Integer contaFavorecida)
			throws Exception {
		// TODO - Implementar
		return null;
	}

	public ComprovanteVO investeEmFundo(ContaVO conta, BigDecimal valor,
			TipoDoFundo tipoDoFundo) throws Exception {
		// TODO - Implementar
		return null;
	}

	public ComprovanteVO realizaDocTed(ContaVO conta, BigDecimal valor,
			Integer bancoFavorecido, Integer agenciaFavorecida,
			Integer contaFavorecida, String cpfDoTitularDaContaFavorecida)
			throws Exception {
		// TODO - Implementar
		return null;
	}

	public List<TransacaoVO> consultaTransacoes(Date dataDeReferencia) {
		List<TransacaoVO> transacoesDoDia = new ArrayList<TransacaoVO>();

		List<Transacao> transacoes = null; //AutorizadorFacade.get()
				//.consultaTransacoes(dataDeReferencia);

		for (Transacao transacao : transacoes) {
			TransacaoVO transacaoVO = new TransacaoVO();

			transacaoVO.setDataHoraTransacao(transacao.getDataHoraCriacao());
			transacaoVO.setTipoDoLancamento(transacao.getTipoDaTransacao()
					.getTipoDoLancamento());
			transacaoVO.setDescricao(transacao.getTipoDaTransacao().getValor());
			transacaoVO.setAgencia(transacao.getAgencia());
			transacaoVO.setConta(transacao.getConta());
			transacaoVO.setValor(new BigDecimal(transacao.getValor()));
			transacaoVO.setIdDaTransacao(transacao
					.getIdentificadorDaTransacao());

			transacoesDoDia.add(transacaoVO);
		}

		return transacoesDoDia;
	}
}