package domain_layer;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Corso {
    private int codice;
    private int capienza;
    private float costo;
    private String tipoCorso;
    private int numTurni;
    protected LinkedList<Cane> elencoCaniIscritti;
    protected LinkedList<Lezione> programma;
    private Lezione lezioneCorrente;
    private Lezione lezioneSelezionata;

    
    //Costruttore dell classe Corso
    public Corso(int codice, int capienza, float costo, String tipoCorso) { //La capienza viene passata da PawBookings
        this.codice = codice;
        this.capienza = capienza;
        this.costo = costo;
        this.tipoCorso = tipoCorso;
        this.elencoCaniIscritti = new LinkedList<>();
        this.programma = new LinkedList<>();
        
    }


    // Metodo per confermare l'iscrizione di un cane e aggiornare la capienza
    public void confermaIscrizione(Cane cn) {
        this.elencoCaniIscritti.add(cn);
        aggiornaCapienza();
    }

    // Metodo per aggiornare la capienza del corso
    public void aggiornaCapienza() {
        this.capienza--;
    }

    public void aggiornaCaniIscritti(Cane cn) {
        this.elencoCaniIscritti.remove(cn);
    }

    // Metodo per ottenere la capienza del corso
    public int getCapienza() {
        return this.capienza;
    }

    public float getCosto() {
        return this.costo;
    }

    public int getCodice() {
        return this.codice;
    }

    public String getTipoCorso() {
        return this.tipoCorso;
    }

    public LinkedList<Cane> getCaniIscritti(){
        return this.elencoCaniIscritti;
    }


    public LinkedList<Lezione> getLezioni() {
        return this.programma;
    }



    public Boolean annullaIscrizione(Cane cn){
        return this.elencoCaniIscritti.remove(cn);
    }


    public boolean aggiornaInformazioni(int capienza, float costo){
        if(capienza <= 0 || costo <= 0){
            return false;
        } else {
            this.capienza = capienza;
            this.costo = costo;
            return true;
        }
    }

    public void nuovaLezione(int codiceLezione, String nome) {
        Lezione nuovaLezione = new Lezione(codiceLezione, nome);
        setLezioneCorrente(nuovaLezione);
    }
    
    public boolean setLezioneCorrente(Lezione nuovaLezione){
        if(nuovaLezione == null){
            return false;
        } else {
            this.lezioneCorrente = nuovaLezione;
            return true;
        }
    }

    public void aggiornaLezione(String nome, String descrizione){
        this.lezioneCorrente.nuovoEsercizio(nome, descrizione);
    }



    public Boolean setLezioneSelezionata(Lezione lz){
        if(lz == null){
            this.lezioneSelezionata = null;
            return false;
        }
        else{
            this.lezioneSelezionata = lz;
            return true;
        }
    }



    public void aggiungiTurnoLezione(LocalDate data, LocalTime oraInizio, LocalTime oraFine){
        int codiceTurno = this.generaCodiceTurno();
        this.lezioneSelezionata.nuovoTurno(codiceTurno, data, oraInizio, oraFine);
    }



    public boolean confermaInserimentoLezione(){
        return this.programma.add(this.lezioneCorrente);
    }
    
    public int generaCodiceTurno(){
        numTurni++;
        return numTurni;
    }

    public Map<String, LinkedList<Lezione>> calcolaStatoAvanzamento(LinkedList<Lezione> lezioniSeguite){
        LinkedList<Lezione> lezioniCorsoSeguite = new LinkedList<>();
        LinkedList<Lezione> lezioniCorsoMancanti = new LinkedList<>();
        Map<String, LinkedList<Lezione>> mappaLezioni = new HashMap<>();  

        for(Lezione l: lezioniSeguite){
            for(Lezione lp: this.programma){
                if(l.equals(lp)){lezioniCorsoSeguite.add(l);
            }
            }
            for(Lezione l2: lezioniSeguite){
                for(Lezione lp: this.programma){
                if(!l2.equals(lp)){lezioniCorsoMancanti.add(l2);
            }
            }
   
            mappaLezioni.put("lezioni seguite", lezioniCorsoSeguite);
            mappaLezioni.put("lezioni mancanti", lezioniCorsoMancanti);
            
            }
        }
        return mappaLezioni;
    }
}
