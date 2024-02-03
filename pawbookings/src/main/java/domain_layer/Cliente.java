package domain_layer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


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
    private int codiceDelega;


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
        this.codiceDelega = 0;
    }

    public int getNumCaniAffido(PeriodoAffido affidoCorrente){
        int numCaniAffido =0;
        PeriodoAffido pa;
        for(Cane c: this.caniPosseduti){
            pa = c.getAffidoCorrente();
            if(affidoCorrente.equals(pa)){
                numCaniAffido++;
            }
        }
        return numCaniAffido;
    }

    public int getNumCaniIscritti(Corso corsoCorrente){
        int numCaniIscritti =0;
        Corso c;
        for(Cane cn: this.caniPosseduti){
            c = cn.getCorsoCorrente();
            if(corsoCorrente.equals(c)
                &&(cn.getTurnoCorrente()!=null)
                &&(cn.getUltimaLezioneSeguita().equals(c.getLezioni().getFirst()))){
                numCaniIscritti++;
            }
        }
        return numCaniIscritti;
    }

    public LinkedList<Cane> getCaniNonInAffido() {
        return this.calcolaCaniNonInAffido();
    }

    //Il metodo restituisce una lista di cani non in affido.
    public LinkedList<Cane> calcolaCaniNonInAffido() {
        LinkedList<Cane> elencoCaniNonInAffido = new LinkedList<>();
        for (Cane cn : this.caniPosseduti) {
            if (!cn.getAttualmenteInAffido()) {
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
        this.setNotifica(false);
        this.statoSaluteCani.clear();
    }
    


    @Override
    public void update(Observable o) {
        PeriodoAffido pa = (PeriodoAffido) o;
        Map<Integer,String> mappaStatoSalute = pa.getState();
        int codiceCane;
        String nomeCane;
        String stato;

        // esempio di un elemento della mappa: {1: "In ottima forma"} 
        Map<String,String> mappaStatoSaluteCani = new HashMap<>();

        for (Cane c: this.caniPosseduti) {
            codiceCane = c.getCodiceCane();
            nomeCane = c.getNome();
            for (Integer codiceCaneMappa: mappaStatoSalute.keySet()) {
                // Se nella mappaStatoSalute vi è un codiceCane corrispondente al codiceCane di un cane del cliente
                // lo aggiungiamo, insieme al relativo stato di salute, alla mappa statoSaluteCani 
                if (codiceCane==codiceCaneMappa) {
                    stato = mappaStatoSalute.get(codiceCane);
                    mappaStatoSaluteCani.put(nomeCane,stato);
                }
            }
        }

        // Aggiungiamo la mappaStatoSaluteCani creata alla lista statoSaluteCani del Cliente
        this.statoSaluteCani.add(mappaStatoSaluteCani);
        this.setNotifica(true);
    }

    public void setNotifica(Boolean val){
        this.notifica = val;
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

   
    public LinkedList<Map<String,String>> getStatoSalute(){
        return this.statoSaluteCani;
    }

    public void setStatoSaluteCani(LinkedList<Map<String, String>> mappaStatoSalute){
        this.statoSaluteCani = mappaStatoSalute;
    }
    public void addCane(Cane cn) {
        this.caniPosseduti.add(cn);
    }

    public void setCodiceDelega(int codiceDelega) {
        this.codiceDelega = codiceDelega;
    }

    public boolean checkSovrapposizioneDate(Cane cn, PeriodoAffido pa){
        if (cn.getTurnoCorrente() == null) {
            return true;
        } else {
            if((cn.getTurnoCorrente().getData().isAfter(pa.getDataInizio())) && (cn.getTurnoCorrente().getData().isBefore(pa.getDataFine()))
                || (cn.getTurnoCorrente().getData().equals(pa.getDataInizio())) 
                || (cn.getTurnoCorrente().getData().equals(pa.getDataFine()))){
                return false;
            }
            else{
                return true;
            }
        }
    }

    
    public int getCodiceDelega(){
        return this.codiceDelega;
    }








}
