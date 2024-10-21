package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTransacao {

    private static final String FILE_NAME = "Transacao.txt";

    // Método para incluir uma nova transação no arquivo
    public void incluir(Transacao transacao) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            // Monta a linha que será escrita no arquivo com os dados da transação
            writer.write(formatarTransacao(transacao));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar todas as transações em que a entidade credora tem o identificador fornecido
    public Transacao[] buscarPorEntidadeCredora(int identificadorEntidadeCredito) {
        List<Transacao> transacoesEncontradas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            // Lê o arquivo linha por linha
            while ((linha = reader.readLine()) != null) {
                Transacao transacao = parseTransacao(linha);
                // Verifica se a entidade credora tem o identificador fornecido
                if (transacao.getEntidadeCredito().getIdentificador() == identificadorEntidadeCredito) {
                    transacoesEncontradas.add(transacao);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Retorna um array com as transações encontradas
        return transacoesEncontradas.toArray(new Transacao[0]);
    }

    // Método auxiliar para formatar os dados da transação em uma string para escrita no arquivo
    private String formatarTransacao(Transacao transacao) {
        StringBuilder sb = new StringBuilder();
        // Formata os dados da entidade credora
        sb.append(transacao.getEntidadeCredito().getIdentificador()).append(";")
          .append(transacao.getEntidadeCredito().getNome()).append(";")
          .append(transacao.getEntidadeCredito().isAutorizadoAcao()).append(";")
          .append(transacao.getEntidadeCredito().getSaldoAcao()).append(";")
          .append(transacao.getEntidadeCredito().getSaldoTituloDivida()).append(";");
        
        // Formata os dados da entidade devedora
        sb.append(transacao.getEntidadeDebito().getIdentificador()).append(";")
          .append(transacao.getEntidadeDebito().getNome()).append(";")
          .append(transacao.getEntidadeDebito().isAutorizadoAcao()).append(";")
          .append(transacao.getEntidadeDebito().getSaldoAcao()).append(";")
          .append(transacao.getEntidadeDebito().getSaldoTituloDivida()).append(";");

        // Formata os dados da ação (caso não seja null)
        if (transacao.getAcao() != null) {
            sb.append(transacao.getAcao().getIdentificador()).append(";")
              .append(transacao.getAcao().getNome()).append(";")
              .append(transacao.getAcao().getDataValidade()).append(";")
              .append(transacao.getAcao().getValorUnitario()).append(";");
        } else {
            sb.append("null;");
        }

        // Formata os dados do título de dívida (caso não seja null)
        if (transacao.getTituloDivida() != null) {
            sb.append(transacao.getTituloDivida().getIdentificador()).append(";")
              .append(transacao.getTituloDivida().getNome()).append(";")
              .append(transacao.getTituloDivida().getDataValidade()).append(";")
              .append(transacao.getTituloDivida().getTaxaJuros()).append(";");
        } else {
            sb.append("null;");
        }

        // Formata os dados da operação
        sb.append(transacao.getValorOperacao()).append(";")
          .append(transacao.getDataHoraOperacao());

        return sb.toString();
    }

    // Método auxiliar para converter uma linha do arquivo em um objeto Transacao
    private Transacao parseTransacao(String linha) {
        String[] campos = linha.split(";");
        
        // Parse dos dados da entidade credora
        int identificadorCredito = Integer.parseInt(campos[0]);
        String nomeCredito = campos[1];
        boolean autorizadoAcaoCredito = Boolean.parseBoolean(campos[2]);
        double saldoAcaoCredito = Double.parseDouble(campos[3]);
        double saldoTituloCredito = Double.parseDouble(campos[4]);

        // Parse dos dados da entidade devedora
        int identificadorDebito = Integer.parseInt(campos[5]);
        String nomeDebito = campos[6];
        boolean autorizadoAcaoDebito = Boolean.parseBoolean(campos[7]);
        double saldoAcaoDebito = Double.parseDouble(campos[8]);
        double saldoTituloDebito = Double.parseDouble(campos[9]);

        // Parse da ação (se existir)
        Transacao.Acao acao = null;
        if (!campos[10].equals("null")) {
            int identificadorAcao = Integer.parseInt(campos[10]);
            String nomeAcao = campos[11];
            String dataValidadeAcao = campos[12];
            double valorUnitarioAcao = Double.parseDouble(campos[13]);
            acao = new Transacao.Acao(identificadorAcao, nomeAcao, dataValidadeAcao, valorUnitarioAcao);
        }

        // Parse do título de dívida (se existir)
        Transacao.TituloDivida tituloDivida = null;
        if (!campos[14].equals("null")) {
            int identificadorTitulo = Integer.parseInt(campos[14]);
            String nomeTitulo = campos[15];
            String dataValidadeTitulo = campos[16];
            double taxaJurosTitulo = Double.parseDouble(campos[17]);
            tituloDivida = new Transacao.TituloDivida(identificadorTitulo, nomeTitulo, dataValidadeTitulo, taxaJurosTitulo);
        }

        // Parse dos dados da operação
        double valorOperacao = Double.parseDouble(campos[18]);
        String dataHoraOperacao = campos[19];

        // Cria o objeto Transacao
        return new Transacao(identificadorCredito, nomeCredito, autorizadoAcaoCredito, saldoAcaoCredito, saldoTituloCredito,
                             identificadorDebito, nomeDebito, autorizadoAcaoDebito, saldoAcaoDebito, saldoTituloDebito,
                             acao, tituloDivida, valorOperacao, dataHoraOperacao);
    }
}
