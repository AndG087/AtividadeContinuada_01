package br.com.cesarschool.poo.titulos.utils;

public abstract class ComparadorPadrao implements Comparador {
    @Override
    public int comparar(Comparavel c1, Comparavel c2) {
        if (c1 == null || c2 == null) {
            throw new IllegalArgumentException("Os objetos a serem comparados não podem ser nulos.");
        }

        // Comparação padrão baseada em equals()
        return c1.equals(c2) ? 0 : (c1.hashCode() - c2.hashCode());
    }
}
