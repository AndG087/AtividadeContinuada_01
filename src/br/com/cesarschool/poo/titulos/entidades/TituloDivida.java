package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDate;

import br.gov.cesarschool.poo.daogenerico.Entidade;

public class TituloDivida extends Entidade{

    private int identificador;
    private String nome;
    private LocalDate dataDeValidade;
    private double taxaJuros;
    private String nomeCredor;

    // Construtor com parâmetros (int, String, LocalDate, double)
    public TituloDivida(int identificador, String nome, LocalDate dataDeValidade, double taxaJuros) {
        this.identificador = identificador;
        this.nome = nome;
        this.dataDeValidade = dataDeValidade;
        this.taxaJuros = taxaJuros;
        this.nomeCredor = "Credor Padrão"; // Definindo valor padrão para o nomeCredor
    }

    // Construtor com todos os parâmetros
    public TituloDivida(int identificador, String nome, LocalDate dataDeValidade, double taxaJuros, String nomeCredor) {
        this.identificador = identificador;
        this.nome = nome;
        this.dataDeValidade = dataDeValidade;
        this.taxaJuros = taxaJuros;
        this.nomeCredor = nomeCredor;
    }

    // Getters e setters
    public int getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public String getNomeCredor() {
        return nomeCredor;
    }
    
    public double calcularPrecoTransacao(double montante) {
        return montante * (1 - taxaJuros / 100.0);
    }
}
