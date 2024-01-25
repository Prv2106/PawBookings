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
    public boolean registraCane(String nome, String razza, int codiceCane){
        Cane nuovoCane = new Cane(codiceCane, nome, razza);
        return caniPosseduti.add(nuovoCane);
    }

    // Il metodo genera un codice cane, che è il risultato della somma tra il numero di cani posseduti e 1.
   
    
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

    public Cane getCane(int codiceCane) {
        for (Cane cn : this.caniPosseduti) {
            if (cn.getCodiceCane() == codiceCane) {
                return cn;
            }
        }
        return null;
    }


    public Boolean rimuoviCane(Cane cn){
        return this.caniPosseduti.remove(cn);
    }

    public String getNumTelefono() {
        return this.numeroTelefono;
    }


}
