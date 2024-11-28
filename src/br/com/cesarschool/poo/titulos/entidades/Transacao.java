package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDateTime;
import br.com.cesarschool.poo.titulos.utils.Comparavel;
import br.gov.cesarschool.poo.daogenerico.Entidade;

public class Transacao extends Entidade implements Comparavel {
    private static final long serialVersionUID = 1L;
    
    private EntidadeOperadora entidadeCredito;
    private EntidadeOperadora entidadeDebito;
    private Acao acao;
    private TituloDivida tituloDivida;
    private double valorOperacao;
    private LocalDateTime dataHoraOperacao;

    // Construtor atualizado considerando a nova versão de TituloDivida
    public Transacao(EntidadeOperadora entidadeCredito, EntidadeOperadora entidadeDebito, Acao acao,
                     TituloDivida tituloDivida, double valorOperacao, LocalDateTime dataHoraOperacao) {
        this.entidadeCredito = entidadeCredito;
        this.entidadeDebito = entidadeDebito;
        this.acao = acao;
        this.tituloDivida = tituloDivida;
        this.valorOperacao = valorOperacao;
        this.dataHoraOperacao = dataHoraOperacao;
    }

    // Getters
    public Acao getAcao() {
        return acao;
    }

    public LocalDateTime getDataHoraOperacao() {
        return dataHoraOperacao;
    }

    public EntidadeOperadora getEntidadeCredito() {
        return entidadeCredito;
    }

    public EntidadeOperadora getEntidadeDebito() {
        return entidadeDebito;
    }

    public TituloDivida getTituloDivida() {
        return tituloDivida;
    }

    public double getValorOperacao() {
        return valorOperacao;
    }

    // Implementação de comparação por dataHoraOperacao
    @Override
    public int comparar(Comparavel c) {
        if (c instanceof Transacao) {
            Transacao outra = (Transacao) c;
            return outra.dataHoraOperacao.compareTo(this.dataHoraOperacao);
        }
        throw new IllegalArgumentException("Comparação com tipo incompatível.");
    }
    // Implementação de getIdUnico
    @Override
    public String getIdUnico() {
        // Verifica se o objeto acao é nulo antes de tentar acessá-lo
        String acaoIdUnico = (acao != null) ? acao.getIdUnico() : "null";

        return String.format("%s_%s_%s_%s",
                entidadeCredito != null ? entidadeCredito.getIdUnico() : "null",
                entidadeDebito != null ? entidadeDebito.getIdUnico() : "null",
                acaoIdUnico,
                dataHoraOperacao.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }

    // Método toString
    @Override
    public String toString() {
        return "Transacao{" +
               "entidadeCredito=" + entidadeCredito +
               ", entidadeDebito=" + entidadeDebito +
               ", acao=" + acao +
               ", tituloDivida=" + tituloDivida +
               ", valorOperacao=" + valorOperacao +
               ", dataHoraOperacao=" + dataHoraOperacao +
               '}';
    }
}
