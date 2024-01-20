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
        
    }


    // metodo per recuperare l'unica istanza della classe PawBookings
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

        caneSelezionato.aggiornaAttualmenteIscritto(cs);

        elencoCorsiDisponibili.clear();

        return true;

    }


    public LinkedList<Turno> prenotaTurnoLezione(){


        Lezione lezioneSuccesiva;

        lezioneSuccesiva = caneSelezionato.getLezioneSuccessiva();
        
        return lezioneSuccesiva.getTurniDisponibili();


    }


    public Boolean selezionaTurno(Turno ts){

    }

    public void selezionaCane(Cane cn){

    }

}
