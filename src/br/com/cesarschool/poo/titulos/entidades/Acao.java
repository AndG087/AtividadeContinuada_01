package br.com.cesarschool.poo.titulos.entidades;


public class Acao extends Ativo {
    private double valorUnitario;


    public Acao(double valorUnitario) {
    	super(identificador, nome, dataDeValidade);
    	this.valorUnitario = valorUnitario;
    }

    
    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double calcularPrecoTransacao(double montante) {
        return montante * valorUnitario;
    }
}
