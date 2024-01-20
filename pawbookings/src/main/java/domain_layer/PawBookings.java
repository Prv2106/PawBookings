package domain_layer;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PawBookings {
    
    private static PawBookings PB;

    private LinkedList<Corso> elencoCorsi;
    private LinkedList<Corso> elencoCorsiDisponibili;
    private Map<Integer, Cliente> clienti;
    private Cane caneSelezionato;

    

    // metodo costruttore
    private PawBookings(){
        this.elencoCorsi = new LinkedList<>();
        this.elencoCorsiDisponibili = new LinkedList<>();
        this.clienti = new HashMap<>();

        this.loadClienti();
        this.loadCorsi();
    }


     // metodo per recuperare l'unica istanza della classe PawBookings per il pattern GoF Singleton
     public static PawBookings getInstance(){

         if(PB == null){
             PB = new PawBookings();
             System.out.println("istanza creata");
         }
         else{
             System.out.println("Istanza già creata");
         }
        
        return PB;
    }


    public void loadClienti(){
        Cliente c1= new Cliente(1,"Alberto","Provenzano");
        Cliente c2= new Cliente(2,"Daniele","Lucifora");
        Cliente c3= new Cliente(3,"Giuseppe","Leocata");
        
        this.clienti.put(1,c1);
        this.clienti.put(2,c2);
        this.clienti.put(3,c3);

        System.out.println("Caricamento Clienti completato con successo!");        
    }

    public void loadCorsi(){
        CorsoBase corsoBase = new CorsoBase(1,10,200.0F,"Corso Base");
        CorsoAvanzato corsoAvanzato = new CorsoAvanzato(2,10,250.0F,"Corso Avanzato");
        CorsoAgility corsoAgility = new CorsoAgility(3,5,300.0F,"Corso Agility");

        this.elencoCorsi.add(corsoBase);
        this.elencoCorsi.add(corsoAvanzato);
        this.elencoCorsi.add(corsoAgility);

        System.out.println("Caricamento Corsi completato con successo!");        

    }





    public LinkedList<Corso> nuovaIscrizioneCorso(){
        int capienza;
        // per ciascun corso presente in elencoCorsi viene verificato che il corso non sia pieno 
        // e in caso affermativo viene aggiunto all'elencoCorsiDisponibili
        for(Corso i: elencoCorsi){
            capienza = i.getCapienza();
            if(capienza > 0){
                elencoCorsiDisponibili.add(i);
            }
        }
        return elencoCorsiDisponibili;
    }

    public Boolean confermaIscrizioneCorso(Corso cs){

        cs.confermaIscrizione(caneSelezionato);

        // l'attributo attualmenteIscritto di caneSelezionato diventa true 
        // e viene inizializzata la variabile corsoCorrente di caneSelezionato
        caneSelezionato.aggiornaAttualmenteIscritto(cs);

        // la lista elencoCorsiDisponibili viene svuotata
        elencoCorsiDisponibili.clear();

        return true;

    }


    public LinkedList<Turno> prenotaTurnoLezione(){
        Lezione lezioneSuccesiva;
        //viene recuperata l'istanza della lezione successiva
        lezioneSuccesiva = caneSelezionato.getLezioneSuccessiva();
        
        // Vengono restituiti i turni disponibili relativi alla lezione successiva
        return lezioneSuccesiva.getTurniDisponibili();
    }


    public Boolean selezionaTurno(Turno ts){
        this.caneSelezionato.aggiornaStatoAvanzamento(ts);
        return true;
    }

    public void selezionaCane(Cane cn){
        this.caneSelezionato = cn;
    }

}
