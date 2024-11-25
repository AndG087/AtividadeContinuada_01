package br.gov.cesarschool.poo.daogenerico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe representa um DAO genérico que inclui, exclui, altera, busca por identificador
 * único e busca todos, qualquer objeto(s) cujo tipo é subtipo de Entidade.
 */
public class DAOSerializadorObjetos {

    private String nomeDiretorio;

    // Construtor que inicializa o nome do diretório com base no tipo da entidade
    public DAOSerializadorObjetos(Class<?> tipoEntidade) {
        this.nomeDiretorio = "dados/" + tipoEntidade.getSimpleName();
        File diretorio = new File(nomeDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdirs(); // Cria o diretório, caso não exista
        }
    }

    // Método para incluir uma entidade
    public boolean incluir(Entidade entidade) {
        String caminhoArquivo = nomeDiretorio + "/" + entidade.getIdUnico() + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para alterar uma entidade
    public boolean alterar(Entidade entidade) {
        String caminhoArquivo = nomeDiretorio + "/" + entidade.getIdUnico() + ".dat";
        File arquivo = new File(caminhoArquivo);
        if (arquivo.exists()) {
            return incluir(entidade); // Sobrescreve o arquivo existente
        }
        return false;
    }

    // Método para excluir uma entidade pelo ID único
    public boolean excluir(String idUnico) {
        String caminhoArquivo = nomeDiretorio + "/" + idUnico + ".dat";
        File arquivo = new File(caminhoArquivo);
        return arquivo.delete(); // Retorna true se o arquivo foi excluído
    }

    // Método para buscar uma entidade pelo ID único
    public Entidade buscar(String idUnico) {
        String caminhoArquivo = nomeDiretorio + "/" + idUnico + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (Entidade) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para buscar todas as entidades
    public Entidade[] buscarTodos() {
        File diretorio = new File(nomeDiretorio);
        File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith(".dat"));
        if (arquivos != null) {
            List<Entidade> entidades = new ArrayList<>();
            for (File arquivo : arquivos) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                    entidades.add((Entidade) ois.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return entidades.toArray(new Entidade[0]);
        }
        return new Entidade[0];
    }
}
