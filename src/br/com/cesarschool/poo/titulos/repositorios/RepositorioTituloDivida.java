package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTituloDivida {

    private static final String FILE_NAME = "TituloDivida.txt";

    // Método para incluir um novo TituloDivida no arquivo
    public boolean incluir(TituloDivida tituloDivida) {
        List<TituloDivida> titulos = lerTitulos();
        // Verifica se o identificador já existe
        for (TituloDivida t : titulos) {
            if (t.getIdentificador() == tituloDivida.getIdentificador()) {
                return false; // Não pode incluir identificador repetido
            }
        }
        // Adiciona nova linha ao arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";" + tituloDivida.getDataValidade() + ";" + tituloDivida.getTaxaJuros());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Método para alterar um TituloDivida existente
    public boolean alterar(TituloDivida tituloDivida) {
        List<TituloDivida> titulos = lerTitulos();
        boolean encontrado = false;

        // Procura o identificador e substitui a linha
        for (int i = 0; i < titulos.size(); i++) {
            if (titulos.get(i).getIdentificador() == tituloDivida.getIdentificador()) {
                titulos.set(i, tituloDivida);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false; // Identificador não encontrado
        }

        return escreverTitulos(titulos); // Atualiza o arquivo
    }

    // Método para excluir um TituloDivida existente
    public boolean excluir(int identificador) {
        List<TituloDivida> titulos = lerTitulos();
        boolean encontrado = false;

        // Procura o identificador e remove o título
        for (int i = 0; i < titulos.size(); i++) {
            if (titulos.get(i).getIdentificador() == identificador) {
                titulos.remove(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false; // Identificador não encontrado
        }

        return escreverTitulos(titulos); // Atualiza o arquivo
    }

    // Método para buscar um TituloDivida pelo identificador
    public TituloDivida buscar(int identificador) {
        List<TituloDivida> titulos = lerTitulos();
        for (TituloDivida t : titulos) {
            if (t.getIdentificador() == identificador) {
                return t; // Retorna o título correspondente
            }
        }
        return null; // Identificador não encontrado
    }

    // Método auxiliar para ler todos os títulos do arquivo
    private List<TituloDivida> lerTitulos() {
        List<TituloDivida> titulos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            // Lê cada linha e converte em objeto TituloDivida
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                int identificador = Integer.parseInt(campos[0]);
                String nome = campos[1];
                String dataValidade = campos[2];
                double taxaJuros = Double.parseDouble(campos[3]);
                titulos.add(new TituloDivida(identificador, nome, dataValidade, taxaJuros));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return titulos;
    }

    // Método auxiliar para escrever todos os títulos no arquivo
    private boolean escreverTitulos(List<TituloDivida> titulos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // Escreve cada título no arquivo
            for (TituloDivida titulo : titulos) {
                writer.write(titulo.getIdentificador() + ";" + titulo.getNome() + ";" + titulo.getDataValidade() + ";" + titulo.getTaxaJuros());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

