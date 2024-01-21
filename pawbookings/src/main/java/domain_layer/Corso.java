package domain_layer;


import java.util.LinkedList;

public abstract class Corso {
    private int codice;
    private int capienza;
    private float costo;
    private String tipoCorso;
    protected LinkedList<Cane> elencoCaniIscritti;
    protected LinkedList<Lezione> programma;

    

    //Costruttore dell classe Corso
    public Corso(int codice, int capienza, float costo, String tipoCorso) { //La capienza viene passata da PawBookings
        this.codice = codice;
        this.capienza = capienza;
        this.costo = costo;
        this.tipoCorso = tipoCorso;
        this.elencoCaniIscritti = new LinkedList<>();
        this.programma = new LinkedList<>();
        
    }


    // metodo implementato dalle sottoclassi di Corso...
    public abstract void loadLezioni();

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

    public String getTipoCorso() {
        return this.tipoCorso;
    }

    public LinkedList<Cane> getCaniIscritti(){
        return this.elencoCaniIscritti;
    }


    public LinkedList<Lezione> getLezioni() {
        return this.programma;
    }


    
}
