package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;
import java.time.LocalDate;

public class MediatorEntidadeOperadora {
    private static MediatorEntidadeOperadora instancia;
    private final RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();
    
    private MediatorEntidadeOperadora() {
    }

    public static synchronized MediatorEntidadeOperadora getInstancia() {
        if (instancia == null) {
            instancia = new MediatorEntidadeOperadora();
        }
        return instancia;
    }

    private String validar(EntidadeOperadora entidade) {
        if (entidade.getIdentificador() <= 100 || entidade.getIdentificador() >= 1000000) {
            return "Identificador deve estar entre 100 e 1000000.";
        }

        String nome = entidade.getNome();
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }

        if (nome.length() < 10 || nome.length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }

        /*LocalDate dataAtualMais180 = LocalDate.now().plusDays(180);
        if (entidade.getDataDeValidade().isBefore(dataAtualMais180)) {
            return "Data de validade deve ser superior a 180 dias a partir da data atual.";
        }

        if (entidade.getValorUnitario() <= 0) {
            return "Valor unitário deve ser maior que zero.";
        }*/

        return null;
    }

    public String incluir(EntidadeOperadora entidade) {
        String mensagemValidacao = validar(entidade);

        if (mensagemValidacao != null) {
            return mensagemValidacao; // (2)
        }

        boolean incluida = repositorioEntidadeOperadora.incluir(entidade);

        if (incluida) {
            return null; // (1)
        } else {
            return "Entidade já existente"; // (3)
        }
    }

    public String alterar(EntidadeOperadora entidade) {
        String mensagemValidacao = validar(entidade);

        if (mensagemValidacao != null) {
            return mensagemValidacao; // (2)
        }

        boolean alterada = repositorioEntidadeOperadora.alterar(entidade);

        if (alterada) {
            return null; // (1)
        } else {
            return "Entidade inexistente"; // (3)
        }
    }

    // Metodo para excluir uma EntidadeOperadora por identificador
    public String excluir(int identificador) {
        if (!validarIdentificador(identificador)) {
            return "Entidade inexistente.";
        }

        boolean excluida = repositorioEntidadeOperadora.excluir(identificador);
        return excluida ? null : "Entidade inexistente.";
    }

    // Metodo para buscar uma EntidadeOperadora por identificador
    public EntidadeOperadora buscar(int identificador) {
        if (!validarIdentificador(identificador)) {
            return null;
        }
        return repositorioEntidadeOperadora.buscar(identificador);
    }

    // Metodo para validar o identificador
    private boolean validarIdentificador(long identificador) {
        return identificador >= 100 && identificador <= 1000000;
    }

}
