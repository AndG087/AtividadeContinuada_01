package br.com.cesarschool.poo.titulos.entidades;

public class Transacao {
	private EntidadeCredito entidadeCredito;
	private EntidadeDebito entidadeDebito;
	private Acao acao;
	private TituloDivida tituloDivida;
	private double valorOperacao;
	private LocalDateTime  dataHoraOperacao;
	
	public Transacao(EntidadeCredito entidadeCredito , EntidadeDebito entidadeDebito , Acao acao , TituloDivida tituloDivida, double valorOperacao , LocalDateTime dataHoraOperacao) {
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
	
	
}
