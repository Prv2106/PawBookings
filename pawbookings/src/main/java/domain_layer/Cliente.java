package domain_layer;

import java.util.LinkedList;

public class Cliente {
    private String codiceCliente;
    private String nome;
    private String cognome;
    private String password;
    private String numeroTelefono;
    private LinkedList<Cane> caniPosseduti;

    public Cliente (String codiceCliente, String nome, String cognome,String password,String numeroTelefono) {
        this.codiceCliente = codiceCliente;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.numeroTelefono = numeroTelefono;
        this.caniPosseduti = new LinkedList<>();
    }

    // public void loadCani(int codiceCane, String nome, String razza){
    //     Cane cn = new Cane(codiceCane, nome, razza);
    //     this.caniPosseduti.add(cn);
    // }

    public LinkedList<Cane> getCaniNonInAffido() {
        return this.calcolaCaniNonInAffido();
    }

    //Il metodo restituisce una lista di cani non in affido.
    public LinkedList<Cane> calcolaCaniNonInAffido() {
        LinkedList<Cane> elencoCaniNonInAffido = new LinkedList<>();
        for (Cane cn : this.caniPosseduti) {
            if (!cn.attualmenteInAffido) {
                elencoCaniNonInAffido.add(cn);
            }
        }
        return elencoCaniNonInAffido;
    }

    // Il metodo registra un cane, aggiungendolo alla lista dei cani posseduti dal cliente.
    public boolean registraCane(String nome, String razza){
        int codCane = generaCodiceCane();
        Cane cn = new Cane(codCane, nome, razza);
        caniPosseduti.add(cn);
        return true;
    }

    // Il metodo genera un codice cane, che è il risultato della somma tra il numero di cani posseduti e 1.
    public int generaCodiceCane(){
        int codiceCane = 0;
        for (Cane cn : this.caniPosseduti) {
            codiceCane = this.caniPosseduti.size() + 1;
        }
        return codiceCane;
    }

    // Metodi per recuperare gli attributi

    public String getCodiceCliente() {
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


    public String getPassword(){
        return this.password;
    }


}
