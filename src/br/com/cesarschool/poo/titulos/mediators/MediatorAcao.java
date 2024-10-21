package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

public class MediatorAcao {
    private static MediatorAcao instancia;
    private final RepositorioAcao repositorioAcao = new RepositorioAcao();

    private MediatorAcao() {}

    public static MediatorAcao getInstancia() {
        if (instancia == null) {
            instancia = new MediatorAcao();
        }
        return instancia;
    }

    private String validar(Acao acao) {
        if (acao.getIdentificador() <= 0 || acao.getIdentificador() >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        if (acao.getNome() == null || acao.getNome().trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }
        if (acao.getNome().length() < 10 || acao.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        if (acao.getDataValidade().isBefore(LocalDate.now().plusDays(30))) {
            return "Data de validade deve ter pelo menos 30 dias à frente da data atual.";
        }
        if (acao.getValorUnitario() <= 0) {
            return "Valor unitário deve ser maior que zero.";
        }
        return null; // Válido
    }

    public String incluir(Acao acao) {
        String validacao = validar(acao);
        if (validacao != null) {
            return validacao;
        }
        if (repositorioAcao.incluir(acao)) {
            return null;
        }
        return "Ação já existente";
    }

    public String alterar(Acao acao) {
        String validacao = validar(acao);
        if (validacao != null) {
            return validacao;
        }
        if (repositorioAcao.alterar(acao)) {
            return null;
        }
        return "Ação inexistente";
    }

    public String excluir(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        if (repositorioAcao.excluir(identificador)) {
            return null;
        }
        return "Ação inexistente";
    }

    public Acao buscar(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioAcao.buscar(identificador);
    }
}
