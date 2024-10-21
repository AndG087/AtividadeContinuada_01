package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

public class MediatorEntidadeOperadora {
    private static MediatorEntidadeOperadora instancia;
    private final RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();

    // Construtor privado para garantir o Singleton
    private MediatorEntidadeOperadora() {}

    public static MediatorEntidadeOperadora getInstancia() {
        if (instancia == null) {
            instancia = new MediatorEntidadeOperadora();
        }
        return instancia;
    }

    private String validar(EntidadeOperadora entidade) {
        if (entidade.getIdentificador() < 100 || entidade.getIdentificador() > 1000000) {
            return "Identificador deve estar entre 100 e 1000000.";
        }
        if (entidade.getNome() == null || entidade.getNome().trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }
        if (entidade.getNome().length() < 5 || entidade.getNome().length() > 60) {
            return "Nome deve ter entre 5 e 60 caracteres.";
        }
        if (entidade.getDataValidade().isBefore(LocalDate.now().plusDays(180))) {
            return "Data de validade deve ter pelo menos 180 dias à frente da data atual.";
        }
        if (entidade.getValorUnitario() <= 0) {
            return "Valor unitário deve ser maior que zero.";
        }
        return null; // Objeto é válido
    }

    public String incluir(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao != null) {
            return validacao;
        }
        if (repositorioEntidadeOperadora.incluir(entidade)) {
            return null;
        }
        return "Entidade já existente";
    }

    public String alterar(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao != null) {
            return validacao;
        }
        if (repositorioEntidadeOperadora.alterar(entidade)) {
            return null;
        }
        return "Entidade inexistente";
    }

    public String excluir(int identificador) {
        if (identificador < 100 || identificador > 1000000) {
            return "Identificador deve estar entre 100 e 1000000.";
        }
        if (repositorioEntidadeOperadora.excluir(identificador)) {
            return null;
        }
        return "Entidade inexistente";
    }

    public EntidadeOperadora buscar(int identificador) {
        if (identificador < 100 || identificador > 1000000) {
            return null;
        }
        return repositorioEntidadeOperadora.buscar(identificador);
    }
}
