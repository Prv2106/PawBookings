package domain_layer;

import java.util.LinkedList;

public class Cliente {
    private int codiceCliente;
    private String nome;
    private String cognome;
    private LinkedList<Cane> caniPosseduti;

    public Cliente (int codiceCliente, String nome, String cognome) {
        this.codiceCliente = codiceCliente;
        this.nome = nome;
        this.cognome = cognome;
        this.caniPosseduti = new LinkedList<>();
    }

    public int getCodiceCliente() {
        return this.codiceCliente;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public LinkedList<Cane> getCani() {
        return this.caniPosseduti;
    }


    public void loadCani(int codiceCane, String nome, String razza){
        Cane cn = new Cane(codiceCane, nome, razza);
        this.caniPosseduti.add(cn);
    }



}
