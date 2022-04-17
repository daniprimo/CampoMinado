package br.com.impacto.cm;

import br.com.impacto.cm.excessao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int LINHA;
    private final int COLUNA;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();

    Campo(int linha, int coluna){
        this.LINHA = linha;
        this.COLUNA = coluna;
    }

    boolean adicionarVizinho(Campo vizinho){
        boolean linhaDiferente = LINHA != vizinho.LINHA;
        boolean colunaDiferente = COLUNA != vizinho.COLUNA;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(LINHA - vizinho.LINHA);
        int deltaColuna = Math.abs(COLUNA - vizinho.COLUNA);
        int deltaGeral = deltaColuna+deltaLinha;

        if (deltaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        }else if (deltaGeral == 2 && diagonal){
            vizinhos.add(vizinho);
            return true;
        }else {
            return false;
        }
    }

    void alternarMarcacao () {
        if (!aberto){
            marcado = !marcado;
        }
    }

    boolean abrir (){
        if (!aberto && !marcado){
            aberto = true;
            if (minado){
                throw new ExplosaoException();
            }
            if (vizinhancaSegura()){
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        }else {
            return false;
        }
    }

    boolean vizinhancaSegura () {
        //Atraves do predicate verificando verificando se tem blocos minados
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public boolean isMarcado () {
        return marcado;
    }


}