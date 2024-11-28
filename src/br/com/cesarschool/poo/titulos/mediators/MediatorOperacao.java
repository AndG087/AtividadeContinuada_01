package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDateTime;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;

public class MediatorOperacao {
    private static MediatorOperacao instancia;
    private final MediatorAcao mediatorAcao = MediatorAcao.getInstancia();
    private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getInstancia();
    private final MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstancia();
    private final RepositorioTransacao repositorioTransacao = new RepositorioTransacao();

    // Construtor privado para garantir o Singleton
    private MediatorOperacao() {}

    public static MediatorOperacao getInstancia() {
        if (instancia == null) {
            instancia = new MediatorOperacao();
        }
        return instancia;
    }

    public String realizarOperacao(boolean ehAcao, int idEntidadeCredito, int idEntidadeDebito, int idAcaoOuTitulo, double valor) {
        // 1. Validar o valor
        if (valor <= 0) {
            return "Valor inválido";
        }

        // 2. Buscar a entidade de crédito
        EntidadeOperadora entidadeCredito = mediatorEntidadeOperadora.buscar(idEntidadeCredito);
        if (entidadeCredito == null) {
            return "Entidade crédito inexistente";
        }

        // 3. Buscar a entidade de débito
        EntidadeOperadora entidadeDebito = mediatorEntidadeOperadora.buscar(idEntidadeDebito);
        if (entidadeDebito == null) {
            return "Entidade débito inexistente";
        }

        // 4 e 5. Verificar autorização para ações
        if (ehAcao) {
            if (!entidadeCredito.isAutorizadaParaAcao()) {
                return "Entidade de crédito não autorizada para ação";
            }
            if (!entidadeDebito.isAutorizadaParaAcao()) {
                return "Entidade de débito não autorizada para ação";
            }
        }

        double valorOperacao;
        if (ehAcao) {
            // 6. Buscar a ação
            Acao acao = mediatorAcao.buscar(idAcaoOuTitulo);
            if (acao == null) {
                return "Ação não encontrada";
            }

            // 7. Validar o saldo de ações
            if (entidadeDebito.getSaldoAcao() < valor) {
                return "Saldo da entidade débito insuficiente";
            }

            // 9. Calcular o valor da operação (para ações, é o próprio valor)
            valorOperacao = valor;

        } else {
            // 6. Buscar o título de dívida
            TituloDivida tituloDivida = mediatorTituloDivida.buscar(idAcaoOuTitulo);
            if (tituloDivida == null) {
                return "Título de dívida não encontrado";
            }

            // 7. Validar o saldo de títulos de dívida
            if (entidadeDebito.getSaldoTituloDivida() < valor) {
                return "Saldo da entidade débito insuficiente";
            }

            // 9. Calcular o valor da operação usando o método calcularPrecoTransacao
            valorOperacao = tituloDivida.calcularPrecoTransacao(valor);
        }

        // 10. Creditar saldo na entidade de crédito
        if (ehAcao) {
            entidadeCredito.creditarSaldoAcao(valorOperacao);
        } else {
            entidadeCredito.creditarSaldoTituloDivida(valorOperacao);
        }

        // 11. Debitar saldo na entidade de débito
        if (ehAcao) {
            entidadeDebito.debitarSaldoAcao(valorOperacao);
        } else {
            entidadeDebito.debitarSaldoTituloDivida(valorOperacao);
        }

        // 12. Alterar a entidade de crédito
        String mensagemAlterarCredito = mediatorEntidadeOperadora.alterar(entidadeCredito);
        if (mensagemAlterarCredito != null) {
            return mensagemAlterarCredito;
        }

        // 13. Alterar a entidade de débito
        String mensagemAlterarDebito = mediatorEntidadeOperadora.alterar(entidadeDebito);
        if (mensagemAlterarDebito != null) {
            return mensagemAlterarDebito;
        }

        // 14. Criar a transação
        Transacao transacao = new Transacao(
            entidadeCredito,
            entidadeDebito,
            ehAcao ? mediatorAcao.buscar(idAcaoOuTitulo) : null,
            !ehAcao ? mediatorTituloDivida.buscar(idAcaoOuTitulo) : null,
            valorOperacao,
            LocalDateTime.now()
        );

        // 15. Incluir a transação no repositório
        repositorioTransacao.incluir(transacao);

        return null; // Operação realizada com sucesso
    }

    public Transacao[] gerarExtrato(int idEntidade) {
        // 2. Buscar transações onde a entidade é credora
        Transacao[] transacoesCredora = repositorioTransacao.buscarPorEntidadeCredora(idEntidade);

        // 3. Buscar transações onde a entidade é devedora
        Transacao[] transacoesDevedora = repositorioTransacao.buscarPorEntidadeDevedora(idEntidade);

        // 4. Combinar as transações em um único array
        Transacao[] todasTransacoes = combinarTransacoes(transacoesCredora, transacoesDevedora);

        // 5. Ordenar o array por data e hora em ordem decrescente
        ordenarTransacoesPorData(todasTransacoes);

        // 6. Retornar o array
        return todasTransacoes;
    }

    private Transacao[] combinarTransacoes(Transacao[] credora, Transacao[] devedora) {
        Transacao[] combinadas = new Transacao[credora.length + devedora.length];
        System.arraycopy(credora, 0, combinadas, 0, credora.length);
        System.arraycopy(devedora, 0, combinadas, credora.length, devedora.length);
        return combinadas;
    }

    private void ordenarTransacoesPorData(Transacao[] transacoes) {
        // Ordenar por data/hora em ordem decrescente
        java.util.Arrays.sort(transacoes, (t1, t2) -> t2.getDataHoraOperacao().compareTo(t1.getDataHoraOperacao()));
    }
}
