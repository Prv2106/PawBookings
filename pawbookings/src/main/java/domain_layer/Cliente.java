package domain_layer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Cliente implements Observer {
    private boolean notifica;
    private String codiceCliente;
    private String nome;
    private String cognome;
    private String password;
    private String numeroTelefono;
    private LinkedList<Cane> caniPosseduti;
    private LinkedList<Map<String, String>> statoSaluteCani;
    private LinkedList<PeriodoAffido> periodiAffido;


    public Cliente (String codiceCliente, String nome, String cognome,String password,String numeroTelefono) {
        this.codiceCliente = codiceCliente;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.numeroTelefono = numeroTelefono;
        this.notifica = false;
        this.caniPosseduti = new LinkedList<>();
        this.statoSaluteCani = new LinkedList<>();
        this.statoSaluteCani = new LinkedList<>();
        this.periodiAffido = new LinkedList<>();
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

    public Boolean rimuoviCane(Cane cn) {
        return this.caniPosseduti.remove(cn);
    }

    public void iscrizioneNotificheStatoSalute(PeriodoAffido periodoSelezionato) {
        periodoSelezionato.addObserver(this);
    }

    public void annullamentoIscrizione(PeriodoAffido periodoCorrente) {
        periodoCorrente.deleteObserver(this);
    }

    public void  resettaStatoSaluteCani() {
        this.notifica = false;
        this.statoSaluteCani.clear();
    }
    


    @Override
    public void update(Observable o, Object arg) {
    
    PeriodoAffido pa = (PeriodoAffido) o;
    Map<Integer,String> mappaStatoSalute = pa.getState();

    // esempio di un elemento della mappa: {1: "In ottima forma"} 
    Map<String,String> mappaStatoSaluteCani = new HashMap<>();

    for (Cane c: this.caniPosseduti) {
        for (Integer codiceCane: mappaStatoSalute.keySet()) {
            // Se nella mappaStatoSalute vi è un codiceCane corrispondente al codiceCane di un cane del cliente
            // lo aggiungiamo, insieme al relativo stato di salute, alla mappa statoSaluteCani 
            if (c.getCodiceCane()==codiceCane) {
                mappaStatoSaluteCani.put(c.getNome(),mappaStatoSalute.get(codiceCane));
            }
        }
    }

    // Aggiungiamo la mappaStatoSaluteCani creata alla lista statoSaluteCani del Cliente
    this.statoSaluteCani.add(mappaStatoSaluteCani);
    this.notifica = true;
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

    public Cane getCane(int codiceCane) {
        for (Cane cn : this.caniPosseduti) {
            if (cn.getCodiceCane() == codiceCane) {
                return cn;
            }
        }
        return null;
    }




    public String getNumTelefono() {
        return this.numeroTelefono;
    }

    public boolean getNotifica() {
        return this.notifica;
    }

   


}
