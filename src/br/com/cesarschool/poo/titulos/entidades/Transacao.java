package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDateTime;
import br.com.cesarschool.poo.titulos.utils.Comparavel;

public class Transacao implements Comparavel {
	private EntidadeCredito entidadeCredito;
	private EntidadeDebito entidadeDebito;
	private Acao acao;
	private TituloDivida tituloDivida;
	private double valorOperacao;
	private LocalDateTime dataHoraOperacao;

	public Transacao(EntidadeCredito entidadeCredito, EntidadeDebito entidadeDebito, Acao acao,
					 TituloDivida tituloDivida, double valorOperacao, LocalDateTime dataHoraOperacao) {
		this.acao = acao;
		this.dataHoraOperacao = dataHoraOperacao;
		this.entidadeCredito = entidadeCredito;
		this.entidadeDebito = entidadeDebito;
		this.tituloDivida = tituloDivida;
		this.valorOperacao = valorOperacao;
	}

	public Acao getAcao() {
		return acao;
	}

	public LocalDateTime getDataHoraOperacao() {
		return dataHoraOperacao;
	}

	public EntidadeCredito getEntidadeCredito() {
		return entidadeCredito;
	}

	public EntidadeDebito getEntidadeDebito() {
		return entidadeDebito;
	}

	public TituloDivida getTituloDivida() {
		return tituloDivida;
	}

	public double getValorOperacao() {
		return valorOperacao;
	}

	/**
	 * Implementação do método comparar da interface Comparavel.
	 * O critério de comparação é baseado na data e hora da operação.
	 */
	@Override
	public int comparar(Comparavel c) {
		if (c instanceof Transacao) {
			Transacao outra = (Transacao) c;
			return this.dataHoraOperacao.compareTo(outra.getDataHoraOperacao());
		}
		throw new IllegalArgumentException("O objeto comparado não é uma Transacao.");
	}
}
