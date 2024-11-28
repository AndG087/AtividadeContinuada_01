package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import java.util.Arrays;

public class RepositorioTransacao extends RepositorioGeral<Transacao> {

	private static final String FILE_NAME = "EntidadeOperadora.txt";

    @Override
    public Class<Transacao> getClasseEntidade() {
        return Transacao.class;
    }

    // Sobrecarga de método para buscar por identificador int
    public Transacao buscar(int idUnico) {
        return super.buscar(String.valueOf(idUnico)); // Converte int para String
    }

    // Sobrecarga de método para buscar por identificador long
    public Transacao buscar(long idUnico) {
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
    
    public Transacao[] buscarPorEntidadeDevedora(long identificadorEntidadeDebito) {
		// Busca transações filtrando pela entidade devedora
		return Arrays.stream(buscarTodos())
				.filter(transacao -> transacao.getEntidadeDebito().getIdentificador() == identificadorEntidadeDebito)
				.toArray(Transacao[]::new);
	}
    
    public Transacao[] buscarPorEntidadeCredora(long identificadorEntidadeCredito) {
		Transacao[] todasTransacoes = buscarTodos();
		System.out.println("Total de transações carregadas: " + todasTransacoes.length);
		Transacao[] filtradas = Arrays.stream(todasTransacoes)
				.filter(transacao -> {
					System.out.println("Verificando transação: " + transacao.getIdUnico());
					return transacao.getEntidadeCredito().getIdentificador() == identificadorEntidadeCredito;
				})
				.toArray(Transacao[]::new);
		System.out.println("Total de transações filtradas: " + filtradas.length);
		return filtradas;
	}
}