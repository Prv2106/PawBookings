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


    public void loadCani(LinkedList<Cane> listaCani){
        this.caniPosseduti.addAll(listaCani);
    }



}
