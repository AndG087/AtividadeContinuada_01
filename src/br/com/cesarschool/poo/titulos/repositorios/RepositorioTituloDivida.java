package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTituloDivida extends RepositorioGeral<TituloDivida> {

	private static final String FILE_NAME = "EntidadeOperadora.txt";

    @Override
    public Class<TituloDivida> getClasseEntidade() {
        return TituloDivida.class;
    }

    // Sobrecarga de método para buscar por identificador int
    public TituloDivida buscar(int idUnico) {
        return super.buscar(String.valueOf(idUnico)); // Converte int para String
    }

    // Sobrecarga de método para buscar por identificador long
    public TituloDivida buscar(long idUnico) {
        return super.buscar(String.valueOf(idUnico)); // Converte long para String
    }

    // Sobrecarga de método para excluir por identificador int
    public boolean excluir(int idUnico) {
        return super.excluir(String.valueOf(idUnico)); // Converte int para String
    }

    // Sobrecarga de método para excluir por identificador long
    public boolean excluir(long idUnico) {
        return super.excluir(String.valueOf(idUnico)); // Converte long para String
    }
}
