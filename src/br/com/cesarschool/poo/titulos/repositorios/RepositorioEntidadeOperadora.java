package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEntidadeOperadora extends RepositorioGeral<EntidadeOperadora>{

    private static final String FILE_NAME = "EntidadeOperadora.txt"; // Arquivo de dados de EntidadeOperadora

    @Override
    public Class<EntidadeOperadora> getClasseEntidade() {
        return EntidadeOperadora.class;
    }

    @Override
    public EntidadeOperadora buscar(int idUnico) {
        return buscar((long) idUnico);  // Converte para long e chama o método genérico
    }

    @Override
    public boolean excluir(int id) {
        return excluir((long) id);  // Converte para long e chama o método genérico
    }

    public boolean incluir(EntidadeOperadora entidade) {
        List<EntidadeOperadora> entidades = lerEntidades();
        for (EntidadeOperadora e : entidades) {
            if (e.getIdentificador() == entidade.getIdentificador()) {
                return false; // Identificador já existe
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(entidade.getIdentificador() + ";" + entidade.getNome() + ";" + entidade.getAutorizadoAcao() + ";" + entidade.getSaldoAcao() + ";" + entidade.getSaldoTituloDivida());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean alterar(EntidadeOperadora entidade) {
        List<EntidadeOperadora> entidades = lerEntidades();
        boolean encontrado = false;

        for (int i = 0; i < entidades.size(); i++) {
            if (entidades.get(i).getIdentificador() == entidade.getIdentificador()) {
                entidades.set(i, entidade); // Substitui a linha
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        return escreverEntidades(entidades);
    }

    public boolean excluir(long identificador) {
        List<EntidadeOperadora> entidades = lerEntidades();
        boolean encontrado = false;

        for (int i = 0; i < entidades.size(); i++) {
            if (entidades.get(i).getIdentificador() == identificador) {
                entidades.remove(i); // Remove a linha
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        return escreverEntidades(entidades);
    }

    public EntidadeOperadora buscar(long identificador) {
        List<EntidadeOperadora> entidades = lerEntidades();
        for (EntidadeOperadora e : entidades) {
            if (e.getIdentificador() == identificador) {
                return e; // Retorna o objeto encontrado
            }
        }
        return null; // Não encontrado
    }

    private List<EntidadeOperadora> lerEntidades() {
        List<EntidadeOperadora> entidades = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                long identificador = Long.parseLong(campos[0]);
                String nome = campos[1];
                double autorizadoAcao = Double.parseDouble(campos[2]);
                double saldoAcao = Double.parseDouble(campos[3]);
                double saldoTituloDivida = Double.parseDouble(campos[4]);
                entidades.add(new EntidadeOperadora(identificador, nome, autorizadoAcao));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entidades;
    }

    private boolean escreverEntidades(List<EntidadeOperadora> entidades) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (EntidadeOperadora entidade : entidades) {
                writer.write(entidade.getIdentificador() + ";" + entidade.getNome() + ";" + entidade.getAutorizadoAcao() + ";" + entidade.getSaldoAcao() + ";" + entidade.getSaldoTituloDivida());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
