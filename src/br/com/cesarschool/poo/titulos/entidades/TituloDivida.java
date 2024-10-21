package br.com.cesarschool.poo.titulos.entidades;


public class TituloDivida extends Ativo{
	private double taxaJuros;
	
	public TituloDivida(double taxaJuros) {
		super(identificador, nome, dataDeValidade);
		this.taxaJuros = taxaJuros;
	}
	
	public double getTaxaJuros() {
		return taxaJuros;
	}
	
	public void setTaxaJuros(double taxaJuros) {
		this.taxaJuros = taxaJuros;
	}
	
	public double calcularPrecoTransacao(double montante) {
		return montante * (1 - taxaJuros/100.0);
	}
}
