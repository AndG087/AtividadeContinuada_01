package br.com.cesarschool.poo.titulos.utils;

public class RelatorioTransacaoBroker {
    private RepositorioTransacao repositorio;

    public RelatorioTransacaoBroker() {
        this.repositorio = new RepositorioTransacao();
    }

    public Transacao[] relatorioTransacaoPorNomeEntidadeCredora() {
        Transacao[] transacoes = repositorio.buscarTodos();
        Ordenador.ordenar(transacoes, new ComparadorTransacaoPorNomeCredora());
        return transacoes;
    }

    public Transacao[] relatorioTransacaoPorDataHora() {
        Transacao[] transacoes = repositorio.buscarTodos();
        Ordenador.ordenar(transacoes);
        return transacoes;
    }
}
