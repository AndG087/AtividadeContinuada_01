package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAcao {

    private static final String FILE_NAME = "Acao.txt";

    public boolean incluir(Acao acao) {
        List<Acao> acoes = lerAcoes();
        for (Acao a : acoes) {
            if (a.getIdentificador() == acao.getIdentificador()) {
                return false; // Não pode incluir identificador repetido
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataValidade() + ";" + acao.getValorUnitario());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean alterar(Acao acao) {
        List<Acao> acoes = lerAcoes();
        boolean encontrado = false;

        for (int i = 0; i < acoes.size(); i++) {
            if (acoes.get(i).getIdentificador() == acao.getIdentificador()) {
                acoes.set(i, acao); // Substitui a linha
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        return escreverAcoes(acoes);
    }

    public boolean excluir(int identificador) {
        List<Acao> acoes = lerAcoes();
        boolean encontrado = false;

        for (int i = 0; i < acoes.size(); i++) {
            if (acoes.get(i).getIdentificador() == identificador) {
                acoes.remove(i); // Remove a linha
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        return escreverAcoes(acoes);
    }

    public Acao buscar(int identificador) {
        List<Acao> acoes = lerAcoes();
        for (Acao a : acoes) {
            if (a.getIdentificador() == identificador) {
                return a; // Retorna o objeto encontrado
            }
        }
        return null; // Não encontrado
    }

    private List<Acao> lerAcoes() {
        List<Acao> acoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                int identificador = Integer.parseInt(campos[0]);
                String nome = campos[1];
                String dataValidade = campos[2];
                double valorUnitario = Double.parseDouble(campos[3]);
                acoes.add(new Acao(identificador, nome, dataValidade, valorUnitario));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acoes;
    }

    private boolean escreverAcoes(List<Acao> acoes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Acao acao : acoes) {
                writer.write(acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataValidade() + ";" + acao.getValorUnitario());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
