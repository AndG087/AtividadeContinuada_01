package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTituloDivida extends RepositorioGeral<TituloDivida> {

    private static final String DIRETORIO_TITULOS = "titulos";  // Definir um diretório para os arquivos
    private static final String FILE_EXTENSION = ".txt";

    // Construtor do RepositorioTituloDivida, que chama o construtor da classe pai (RepositorioGeral)
    public RepositorioTituloDivida() {
        super(); // Chamando o construtor da classe pai (RepositorioGeral)
    }

    @Override
    public Class<TituloDivida> getClasseEntidade() {
        return TituloDivida.class;
    }

    @Override
    public TituloDivida buscar(int idUnico) {
        return buscar(String.valueOf(idUnico));  // Método genérico da classe pai
    }

    @Override
    public boolean excluir(int id) {
        return excluir(String.valueOf(id));  // Método genérico da classe pai
    }

    @Override
    public boolean incluir(TituloDivida tituloDivida) {
        List<TituloDivida> titulos = lerTitulos();
        for (TituloDivida t : titulos) {
            if (t.getIdentificador() == tituloDivida.getIdentificador()) {
                return false; // Identificador já existe
            }
        }
        
        // Criação do arquivo do título
        String arquivoTitulo = obterCaminhoArquivo(tituloDivida);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTitulo))) {
            writer.write(tituloDivida.getIdentificador() + ";" +
                         tituloDivida.getNome() + ";" +  // nome herdado da classe Ativo
                         tituloDivida.getDataDeValidade() + ";" + 
                         tituloDivida.getTaxaJuros() + ";" +
                         tituloDivida.getNomeCredor());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean alterar(TituloDivida tituloDivida) {
        List<TituloDivida> titulos = lerTitulos();
        boolean encontrado = false;

        for (int i = 0; i < titulos.size(); i++) {
            if (titulos.get(i).getIdentificador() == tituloDivida.getIdentificador()) {
                titulos.set(i, tituloDivida); // Substitui a linha
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        return escreverTitulos(titulos);
    }

    private List<TituloDivida> lerTitulos() {
        List<TituloDivida> titulos = new ArrayList<>();
        File dir = new File(DIRETORIO_TITULOS);
        if (!dir.exists()) {
            dir.mkdir();  // Cria o diretório caso não exista
        }
        File[] arquivos = dir.listFiles((dir1, name) -> name.endsWith(FILE_EXTENSION));

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        String[] campos = linha.split(";");
                        int identificador = Integer.parseInt(campos[0]);
                        String nome = campos[1];
                        LocalDate dataDeValidade = LocalDate.parse(campos[2]);
                        double taxaJuros = Double.parseDouble(campos[3]);
                        String nomeCredor = campos[4];  // Adicionando o campo nomeCredor
                        titulos.add(new TituloDivida(identificador, nome, dataDeValidade, taxaJuros, nomeCredor));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return titulos;
    }

    private boolean escreverTitulos(List<TituloDivida> titulos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(obterCaminhoArquivo(titulos.get(0))))) {
            for (TituloDivida titulo : titulos) {
                writer.write(titulo.getIdentificador() + ";" +
                             titulo.getNome() + ";" +
                             titulo.getDataDeValidade().toString() + ";" +
                             titulo.getTaxaJuros() + ";" +
                             titulo.getNomeCredor());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String obterCaminhoArquivo(TituloDivida tituloDivida) {
        return DIRETORIO_TITULOS + File.separator + tituloDivida.getIdentificador() + FILE_EXTENSION;
    }
}
