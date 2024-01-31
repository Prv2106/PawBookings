package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;


/*
 * Test dei casi d'uso UC1-UC2
 */


class PawBookingsTest1 {

    static PawBookings PB;
    static Corso corsoBase;
    static Cane Stella;
    static Cane Asso;
    static Cane Dog;
    static Cane Maya;

    @BeforeAll
    public static void initTest() {
        //Configurazioni che ci servono prima dell'esecuzione dei metodi di test
        PB = PawBookings.getInstance();
        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.aggiungiCane("Dog", "Corso");
        PB.aggiungiCane("Maya", "Pastore Tedesco");
        PB.logout();
        Stella = PB.getClienti().get("Alberto1").getCane(1);
        Asso = PB.getClienti().get("Alberto1").getCane(2);
        Dog = PB.getClienti().get("Alberto1").getCane(3);
        Maya = PB.getClienti().get("Alberto1").getCane(4);

        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        corsoBase = PB.getCorsi().getFirst();
        PB.selezionaCorso(corsoBase);

        // Il codice di questa lezione sarà 1
        PB.nuovaLezione("Comandi di Base");
        PB.inserisciEsercizio("Saluto amichevole", "Questo esercizio aiuta a promuovere una socializzazione positiva tra cani");
        PB.inserisciEsercizio("Resta", "'Resta'per rimanere in posizione, aumentando gradualmente la distanza dal proprietario");
        PB.inserisciEsercizio("Richiamo", "Insegna al cane a rispondere al comando di richiamo del padrone");
        PB.confermaLezione();

        // Il codice di questa lezione sarà 2
        PB.nuovaLezione("Guinzaglio e Camminare al Guinzaglio");
        PB.inserisciEsercizio("Introduzione al Guinzaglio", "Insegnare ai proprietari come presentare il guinzaglio al cane in modo positivo, facendolo abituare gradualmente alla sensazione e premiando il comportamento calmo e collaborativo.");
        PB.inserisciEsercizio("Camminata Focalizzata", "Praticare una camminata controllata in cui il cane cammina al fianco del proprietario senza tirare al guinzaglio. Utilizzare comandi verbali e premi per incoraggiare un comportamento desiderato.");
        PB.inserisciEsercizio("Affrontare Distrazioni", "Introdurre gradualmente distrazioni durante la camminata al guinzaglio, come altri cani o stimoli ambientali. Insegnare ai proprietari come gestire le situazioni, mantenendo il controllo del cane e premiando il comportamento desiderato in presenza di distrazioni.");
        PB.confermaLezione();

        // Inserimento turni
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));

        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);
        Corso corsoAvanzato = PB.modificaProgrammaCorso().get(1);
        PB.selezionaCorso(corsoAvanzato);

        // Il codice di questa lezione sarà 3
        PB.nuovaLezione("Svolte, dietro front e variazioni andature");
        PB.inserisciEsercizio("Dietro Front", "Insegna al cane a girare rapidamente di 180 gradi su comando, promuovendo una risposta veloce e coordinata.");
        PB.inserisciEsercizio("Variazioni Andature", "Pratica diverse andature come il passo veloce e il passo lento, migliorando il controllo del cane nelle varie situazioni.");
        PB.inserisciEsercizio("Svolte", "Esercita il cane a eseguire svolte a destra e sinistra durante la camminata al guinzaglio, migliorando la coordinazione e la capacità di seguire le indicazioni del proprietario.");
        PB.confermaLezione();

        // Il codice di questa lezione sarà 4
        PB.nuovaLezione("Riporto in piano di un oggetto");
        PB.inserisciEsercizio("Introduzione al Riporto", "Insegna al cane a prendere un oggetto e a rilasciarlo gentilmente, utilizzando rinforzi positivi come premi o carezze.");
        PB.inserisciEsercizio("Riporto con Distrazioni", " Pratica il riporto introducendo distrazioni come suoni o altri oggetti, migliorando la capacità del cane di concentrarsi sul compito principale.");
        PB.inserisciEsercizio("Riporto a Distanza", "Insegna al cane a riportare un oggetto anche a distanza, incrementando gradualmente la distanza tra il proprietario e il punto in cui l'oggetto deve essere riportato.");
        PB.confermaLezione();

        // Inserimento turni

        PB.selezionaCorsoModificaTurni(corsoAvanzato);
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(11, 0), LocalTime.of(12, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));

    }

    // Il metodo restituisce un'istanza della classe PawBookings se PB è null.
    @Test
    void testGetInstance() {
        assertNotNull(PB);
    }
    

    // Il metodo verifica che la lista dei corsi disponibili contenga corsi 
    // la cui capienza sia effettivamente maggiore di 0
    @Test
    void testNuovaIscrizioneCorso() {
        LinkedList<Corso> elencoCorsiDisponibili = new LinkedList<>();
        LinkedList<Corso> elencoCorsiCompleto = new LinkedList<>();
        PB.inserisciNuovoCorso("Corso Pieno", 0, 100F);
        elencoCorsiCompleto = PB.getCorsi();

        PB.selezionaCane(Stella);
        elencoCorsiDisponibili = PB.nuovaIscrizioneCorso();
        for (Corso corso : elencoCorsiDisponibili) {
            assertTrue(corso.getCapienza() > 0);
        }

        // Verifichiamo che l'elencoCorsiCompleto (contenente Corso Pieno) non sia uguale all'elencoCorsiDisponibili
        assertFalse(elencoCorsiDisponibili.containsAll(elencoCorsiCompleto));
        // Verifichiamo che elencoCorsiDisponibili non contenga Corso Pieno
        assertFalse(elencoCorsiDisponibili.contains(PB.getCorsi().getLast()));
    }

    @Test
    void testconfermaIscrizioneCorso(){
        // Dog non è iscritto ad alcun Corso
        assertFalse(Dog.getAttualmenteIscritto());
        PB.selezionaCane(Dog);
        // Iscriviamo Dog al corsoBase
        PB.confermaIscrizioneCorso(corsoBase);
        // verifichiamo che Dog sia iscritto ad un corso
        assertTrue(Dog.getAttualmenteIscritto());
        // verifichiamo che Dog sia effettivamente iscritto al corsoBase
        assertEquals(corsoBase, Dog.getCorsoCorrente());
    }


    @Test
    void testPrenotaTurnoLezione(){
        LinkedList<Turno> elencoTurniDisponibili = new LinkedList<>();
        // Primo turno della lezione 2 di corsoBase
        Turno t1 = corsoBase.getLezioni().getLast().getTurniDisponibili().getFirst();
        // Secondo turno della lezione 2 di corsoBase
        Turno t2 = corsoBase.getLezioni().getLast().getTurniDisponibili().getLast();

        // Rimuoviamo il turno t1 dai turni disponibili
        corsoBase.getLezioni().getLast().getTurniDisponibili().remove(t1);

        // Iscriviamo Maya al corsoBase
        PB.selezionaCane(Maya);
        PB.confermaIscrizioneCorso(corsoBase);

        // ipotizziamo che Maya abbia seguito la prima lezione
        Maya.getLezioniSeguite().add(corsoBase.getLezioni().getFirst());

        // Adesso chiamando il metodo prenotaTurnoLezione 
        // Ci aspettiamo che venga restituito l'elenco turni disponibili e che:
        // - tra di essi non ci sia t1
        // - tra di essi ci sia t2
        elencoTurniDisponibili = PB.prenotaTurnoLezione();
        assertFalse(elencoTurniDisponibili.contains(t1));
        assertFalse(elencoTurniDisponibili.contains(t2));

        // Prenotando Maya al turno t2 e riprovando ad eseguire l'operazione prenotaTurnoLezione
        // Ci aspettiamo che venga restituito null poiché il turnoCorrente di Maya è !=null
        PB.selezionaTurno(t2);
        assertNull(PB.prenotaTurnoLezione());
    }





    // Viene fatto un controllo sul valore booleano restituito relativamente al turno passato come parametro al metodo selezionaTurno
    @Test
    void testSelezionaTurno() {
        // Recuperiamo il primo turno lezione 1 del corsoBase
        Turno ts = corsoBase.getLezioni().get(0).getTurniDisponibili().get(0);
        // Iscriviamo Asso al corsoBase
        PB.selezionaCane(Asso);
        PB.confermaIscrizioneCorso(corsoBase);
        assertTrue(PB.selezionaTurno(ts)); 
        // Verifichiamo che il turno corrente di Asso sia effettivamente ts
        assertEquals(ts, Asso.getTurnoCorrente());
        // Verifichiamo che ts non appartenga più ai turni disponibili della lezione 1 del corsoBase
        assertFalse(corsoBase.getLezioni().getFirst().getTurniDisponibili().contains(ts));
    }

    
    // Il metodo restituisce un'istanza della classe Cane se il parametro cane è diverso da null 
    // ed inoltre verifica che il nome del cane sia uguale a "Luna"
    @Test
    void testSelezionaCane() {
        PB.selezionaCane(new Cane(10, "Luna", "Barboncino"));
        assertNotNull(PB.getCaneSelezionato());
        assertEquals("Luna", PB.getCaneSelezionato().getNome());
    }



  
}