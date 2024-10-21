package org.cesarschool.telas;

import java.util.HashMap;
import java.util.Map;

public class EntidadeMediator {
    private Map<String, Entidade> entidadeMap = new HashMap<>();

    public String incluir(Entidade ent) {
        String msg = validar(ent);
        if (msg == null) {
            if (entidadeMap.containsKey(ent.getCodigo())) {
                return "Código já existente!";
            }
            entidadeMap.put(ent.getCodigo(), ent);
            return "Entidade incluída com sucesso!";
        }
        return msg;
    }

    public String alterar(Entidade ent) {
        String msg = validar(ent);
        if (msg == null) {
            if (!entidadeMap.containsKey(ent.getCodigo())) {
                return "Entidade não encontrada!";
            }
            entidadeMap.put(ent.getCodigo(), ent); // Atualiza a entidade
            return "Entidade alterada com sucesso!";
        }
        return msg;
    }

    public String excluir(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "Código inválido!";
        }
        if (entidadeMap.remove(codigo) != null) {
            return "Entidade excluída com sucesso!";
        } else {
            return "Entidade não encontrada!";
        }
    }

    public Entidade buscar(String codigo) {
        return entidadeMap.get(codigo);
    }

    private String validar(Entidade ent) {
        if (ent.getNome() == null || ent.getNome().trim().equals("")) {
            return "Nome deve ser preenchido";
        }
        return null;
    }
}
