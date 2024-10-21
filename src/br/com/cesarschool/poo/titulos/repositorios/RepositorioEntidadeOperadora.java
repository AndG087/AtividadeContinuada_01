package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEntidadeOperadora {

    private static final String FILE_NAME = "Acao.txt";

    // Método para incluir uma nova ação no arquivo
    public boolean incluir(Acao acao) {
        List<Acao> acoes = lerAcoes();
        // Verifica se o identificador já existe
        for (Acao a : acoes) {
            if (a.getIdentificador() == acao.getIdentificador()) {
                return false; // Não pode incluir identificador repetido
            }
        }
        // Adiciona nova linha ao arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataValidade() + ";" + acao.getValorUnitario());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Método para alterar uma ação existente
    public boolean alterar(Acao acao) {
        List<Acao> acoes = lerAcoes();
        boolean encontrado = false;

        // Procura o identificador e substitui a linha
        for (int i = 0; i < acoes.size(); i++) {
            if (acoes.get(i).getIdentificador() == acao.getIdentificador()) {
                acoes.set(i, acao);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false; // Identificador não encontrado
        }

        return escreverAcoes(acoes); // Atualiza o arquivo
    }

    // Método para excluir uma ação existente
    public boolean excluir(int identificador) {
        List<Acao> acoes = lerAcoes();
        boolean encontrado = false;

        // Procura o identificador e remove a ação
        for (int i = 0; i < acoes.size(); i++) {
            if (acoes.get(i).getIdentificador() == identificador) {
                acoes.remove(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false; // Identificador não encontrado
        }

        return escreverAcoes(acoes); // Atualiza o arquivo
    }

    // Método para buscar uma ação pelo identificador
    public Acao buscar(int identificador) {
        List<Acao> acoes = lerAcoes();
        for (Acao a : acoes) {
            if (a.getIdentificador() == identificador) {
                return a; // Retorna a ação correspondente
            }
        }
        return null; // Identificador não encontrado
    }

    // Método auxiliar para ler todas as ações do arquivo
    private List<Acao> lerAcoes() {
        List<Acao> acoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            // Lê cada linha e converte em objeto Acao
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

    // Método auxiliar para escrever todas as ações no arquivo
    private boolean escreverAcoes(List<Acao> acoes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // Escreve cada ação no arquivo
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
