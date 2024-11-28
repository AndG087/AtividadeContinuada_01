package br.gov.cesarschool.poo.daogenerico;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Esta classe representa uma superclasse de todas as entidades.
 */
public abstract class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime dataHoraInclusao;
    private LocalDateTime dataHoraUltimaAlteracao;
    private String usuarioInclusao;
    private String usuarioUltimaAlteracao;
    private String idUnico; // Novo campo para ID único

    // Construtor sem parâmetros
    public Entidade() {
        this.dataHoraInclusao = LocalDateTime.now();
        this.dataHoraUltimaAlteracao = LocalDateTime.now();
        this.usuarioInclusao = "sistema";
        this.usuarioUltimaAlteracao = "sistema";
        this.idUnico = UUID.randomUUID().toString(); // Gerando ID único
    }

    public Entidade(String valueOf) {
		// TODO Auto-generated constructor stub
	}

	// Getters e Setters
    public LocalDateTime getDataHoraInclusao() {
        return dataHoraInclusao;
    }

    public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
        this.dataHoraInclusao = dataHoraInclusao;
    }

    public LocalDateTime getDataHoraUltimaAlteracao() {
        return dataHoraUltimaAlteracao;
    }

    public void setDataHoraUltimaAlteracao(LocalDateTime dataHoraUltimaAlteracao) {
        this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
    }

    public String getUsuarioInclusao() {
        return usuarioInclusao;
    }

    public void setUsuarioInclusao(String usuarioInclusao) {
        this.usuarioInclusao = usuarioInclusao;
    }

    public String getUsuarioUltimaAlteracao() {
        return usuarioUltimaAlteracao;
    }

    public void setUsuarioUltimaAlteracao(String usuarioUltimaAlteracao) {
        this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
    }

    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }	
}
