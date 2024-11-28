package br.gov.cesarschool.poo.daogenerico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DAOSerializadorObjetos {

    private final String diretorio;

    public DAOSerializadorObjetos(Class<?> tipoEntidade) {
        this.diretorio = "./" + tipoEntidade.getSimpleName(); // Define o diretório
        File dir = new File(diretorio);
        if (!dir.exists()) {
            dir.mkdirs(); // Cria o diretório caso não exista
        }
    }

    public boolean incluir(Entidade entidade) {
        File arquivo = new File(diretorio, entidade.getIdUnico());
        System.out.println("Tentando adicionar arquivo no método adicionar: " + arquivo.getAbsolutePath());

        if (arquivo.exists()) {
            System.out.println("Arquivo já presente: " + arquivo.getName());
            return false; // Já existe um arquivo com o mesmo identificador
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            entidade.setDataHoraInclusao(java.time.LocalDateTime.now());
            oos.writeObject(entidade);
            System.out.println("Arquivo gravado com sucesso: " + arquivo.getAbsolutePath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Entidade entidade) {
        File arquivo = new File(diretorio, entidade.getIdUnico());
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado para atualização: " + arquivo.getAbsolutePath());
            return false;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            entidade.setDataHoraUltimaAlteracao(java.time.LocalDateTime.now());
            oos.writeObject(entidade);
            System.out.println("Arquivo atualizado com sucesso: " + arquivo.getAbsolutePath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(String idUnico) {
        File arquivo = new File(diretorio, idUnico);

        if (arquivo.exists() && arquivo.delete()) {
            System.out.println("Arquivo deletado com sucesso: " + arquivo.getAbsolutePath());
            return true;
        } else {
            System.out.println("Erro ao deletar o arquivo: " + arquivo.getAbsolutePath());
            return false;
        }
    }

    public Entidade buscar(String idUnico) {
        File arquivo = new File(diretorio, idUnico);
        System.out.println("Tentando buscar arquivo: " + arquivo.getAbsolutePath());

        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado: " + arquivo.getAbsolutePath());
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Entidade entidade = (Entidade) ois.readObject();
            System.out.println("Entidade deserializada com sucesso: " + entidade);
            return entidade;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao deserializar o arquivo: " + arquivo.getAbsolutePath());
            e.printStackTrace();
            return null;
        }
    }

    public Entidade[] buscarTodos() {
        List<Entidade> entidades = new ArrayList<>();
        File pasta = new File(diretorio);

        // Filtrar todos os arquivos no diretório
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                    entidades.add((Entidade) ois.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Erro ao deserializar arquivo: " + arquivo.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Total de entidades carregadas: " + entidades.size());
        return entidades.toArray(new Entidade[0]);
    }
}