/*
 * Esta classe deve ter os seguintes atributos:
 * identificador, do tipo int
 * nome, do tipo String
 * data de validade, do tipo LocalDate
 *
 * Deve ter um construtor público que inicializa os atributos,
 * e métodos set/get públicos para os atributos. O atributo identificador
 * é read-only fora da classe.
 */
package br.com.cesarschool.poo.titulos.entidades;

import br.gov.cesarschool.poo.daogenerico.Entidade;
import java.time.LocalDate;

public class Ativo extends Entidade {
    private static final long serialVersionUID = 1L;

    private int identificador;
    private String nome;
    private LocalDate dataDeValidade;

    public Ativo(int identificador, String nome, LocalDate dataDeValidade) {
        super(String.valueOf(identificador));
        this.identificador = identificador;
        this.nome = nome;
        this.dataDeValidade = dataDeValidade;
    }

    @Override
    public String getIdUnico() {
        System.out.println("getIdUnico chamado. Retornando: " + identificador);
        return String.valueOf(identificador);
    }

    // Métodos get/set
    public int getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(LocalDate dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
}