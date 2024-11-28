package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTituloDivida;

public class MediatorTituloDivida {

    // Atributo Singleton
    private static MediatorTituloDivida instancia;

    // Atributo repositório, inicializado na declaração
    private RepositorioTituloDivida repositorioTituloDivida = new RepositorioTituloDivida();

    // Construtor privado para o padrão Singleton
    private MediatorTituloDivida() {
    }

    // Método para obter a instância Singleton
    public static MediatorTituloDivida getInstancia() {
        if (instancia == null) {
            instancia = new MediatorTituloDivida();
        }
        return instancia;
    }

    // Método privado para validar o TituloDivida
    private String validar(TituloDivida titulo) {
        if (titulo.getIdentificador() <= 0 || titulo.getIdentificador() >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        if (titulo.getNome() == null || titulo.getNome().isBlank()) {
            return "Nome deve ser preenchido.";
        }
        if (titulo.getNome().length() < 10 || titulo.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        if (titulo.getDataDeValidade().isBefore(LocalDate.now().plusDays(180))) {
            return "Data de validade deve ter pelo menos 180 dias na frente da data atual.";
        }
        if (titulo.getTaxaJuros() < 0) {
            return "Taxa de juros deve ser maior ou igual a zero.";
        }
        return null; // Objeto válido
    }

    // Método para incluir o título
    public String incluir(TituloDivida titulo) {
        String validacao = validar(titulo);
        if (validacao == null) {
            boolean resultado = repositorioTituloDivida.incluir(titulo);
            return resultado ? null : "Título já existente";
        }
        return validacao;
    }

    // Método para alterar o título
    public String alterar(TituloDivida titulo) {
        String validacao = validar(titulo);
        if (validacao == null) {
            boolean resultado = repositorioTituloDivida.alterar(titulo);
            return resultado ? null : "Título inexistente";
        }
        return validacao;
    }

    // Método para excluir o título
    public String excluir(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return "Identificador inválido";
        }
        boolean resultado = repositorioTituloDivida.excluir(identificador);
        return resultado ? null : "Título inexistente";
    }

    // Método para buscar o título
    public TituloDivida buscar(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioTituloDivida.buscar(identificador);
    }
}
