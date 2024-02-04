package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.Lezione;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;


/*
 * Test degli scenari alternativi 3c e 3d dell UC2:Gestisci prenotazione turno lezione
 */


public class PawBookingsTest6 {
    static PawBookings PB;
    static Cane Stella;
    static Cane Duchessa;
    static Corso corsoBase;
    static Corso corsoAvanzato;
    static Corso corsoAgility;
    static Cane Walker;
    static Cane Maya;
    static PeriodoAffido periodo2;
    static Cane Leila;
    static Cane Sole;
    static Cliente Alberto;
    static Cliente Daniele;
    static Cliente Giuseppe;



    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings

    @BeforeAll
    public static void initTest() {
        PB = PawBookings.getInstance();
        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.aggiungiCane("Maya", "Pastore Tedesco");
        PB.aggiungiCane("Leila", "Labrador");
        PB.aggiungiCane("Duchessa", "Labrador");
        PB.logout();
        
        PB.registrati("Giuseppe", "Leocata", "562562", "0000");
        PB.aggiungiCane("Walker", "Pastore Tedesco");
        PB.logout();

        PB.registrati("Daniele", "Lucifora", "9921319", "0000");
        PB.aggiungiCane("Sole", "Barboncino");
        PB.aggiungiCane("Luna", "Rottweiler");
        PB.aggiungiCane("Vanessa", "Labrador");
        PB.logout();

        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        corsoBase = PB.modificaProgrammaCorso().get(0);
        PB.selezionaCorso(corsoBase);


        PB.nuovaLezione("Comandi di Base");
        PB.inserisciEsercizio("Saluto amichevole", "Questo esercizio aiuta a promuovere una socializzazione positiva tra cani");
        PB.confermaLezione();

        PB.nuovaLezione("Guinzaglio e Camminare al Guinzaglio");
        PB.inserisciEsercizio("Introduzione al Guinzaglio", "Insegnare ai proprietari come presentare il guinzaglio al cane in modo positivo, facendolo abituare gradualmente alla sensazione e premiando il comportamento calmo e collaborativo.");
        PB.confermaLezione();

        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);
        corsoAvanzato = PB.modificaProgrammaCorso().get(1);
        PB.selezionaCorso(corsoAvanzato);

        PB.nuovaLezione("Svolte, dietro front e variazioni andature");
        PB.inserisciEsercizio("Dietro Front", "Insegna al cane a girare rapidamente di 180 gradi su comando, promuovendo una risposta veloce e coordinata.");
        PB.confermaLezione();

        PB.nuovaLezione("Riporto in piano di un oggetto");
        PB.inserisciEsercizio("Introduzione al Riporto", "Insegna al cane a prendere un oggetto e a rilasciarlo gentilmente, utilizzando rinforzi positivi come premi o carezze.");
        PB.confermaLezione();

        // Inserimento turni 4 per la lezione 1 e 2 per la lezione 2
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(11, 0), LocalTime.of(12, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        

        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
      
        PB.inserisciNuovoCorso("Corso Agility", 10, 300.0F);
        corsoAgility = PB.getCorsi().get(2);
        PB.selezionaCorso(corsoAgility);

        PB.nuovaLezione("Abilità di Controllo e Fiducia");
        PB.inserisciEsercizio("Pausa", "Insegna al cane il comando \"Pausa\" per fermarsi istantaneamente, sviluppando il controllo e la risposta rapida alle istruzioni del proprietario.");
        PB.confermaLezione();

        // Iscriviamo Stella al corsoBase
        PB.accedi("Alberto1", "0000");
        Alberto = PB.getClienteLoggato();
        Stella = Alberto.getCane(1);
        PB.setCaneSelezionato(Stella);
        PB.confermaIscrizioneCorso(corsoBase);

        // Iscriviamo Maya al corsoBase
        Maya = Alberto.getCane(3);
        PB.setCaneSelezionato(Maya);
        PB.confermaIscrizioneCorso(corsoBase);

        // Iscriviamo Maya al corsoBase
        Leila = Alberto.getCane(4);
        PB.selezionaPeriodo(PB.getPeriodiAffido().getLast());
        PB.confermaAffido(Leila);

        
        // Mettiamo Stella in Affido nel periodo1
        PB.selezionaPeriodo(PB.getPeriodiAffido().getFirst());
        PB.confermaAffido(Stella);

        // Facciamo in modo che Duchessa abbia completato tutti i corsi
        Duchessa = Alberto.getCane(5);
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(0));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(1));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(2));


        // Iscriviamo Walker al corsoBase
        PB.accedi("Giuseppe2", "0000");
        Giuseppe = PB.getClienteLoggato();
        Walker = Giuseppe.getCane(6);
        PB.setCaneSelezionato(Walker);
        PB.confermaIscrizioneCorso(corsoBase);

        // Mettiamo Walker in affido nel periodo2
        PB.selezionaPeriodo(PB.getPeriodiAffido().get(1));
        PB.confermaAffido(Walker);


        // Iscriviamo Sole al corsoBase
        PB.accedi("Daniele3", "0000");
        Daniele = PB.getClienteLoggato();
        Sole = Daniele.getCani().getFirst();
        PB.setCaneSelezionato(Sole);
        PB.confermaIscrizioneCorso(corsoBase);
        PB.logout();
        

    }



    /* Test dell'estensione 3c dell'UC2 */
    @Test
    void testDisdiciTurno(){
        // Prenotiamo Sole di Daniele al primo turno del corsoBase 
        PB.selezionaCane(Sole);
        Turno ts = corsoBase.getLezioni().getFirst().getElencoTurniDisponibili().getFirst();
        Lezione l1 = corsoBase.getLezioni().getFirst();
        Lezione l2 =corsoBase.getLezioni().get(1);
        Turno tl2 = l2.getElencoTurniDisponibili().getFirst();
        
        PB.selezionaTurno(ts);
        // Adesso la prima lezione del corsoBase è tra le lezioni seguite di Sole
        assertTrue(Sole.getLezioniSeguite().contains(corsoBase.getLezioni().getFirst()));
        assertEquals(Sole.getTurnoCorrente(), ts);
        assertFalse(l1.getElencoTurniDisponibili().contains(ts));
        // Test del metodo
        // Chiamando il metodo disdiciTurno
        // Ci aspettiamo che il turnoCorrente di Sole diventi null
        // Ci aspettiamo che il turno ts appartenga nuovamente all'elencoTurniDisponibili della lezione l1
        // Ci aspettiamo che la prima lezione del corsoBase non sia più tra le lezioniSeguite di Sole
        PB.disdiciTurno();
        assertFalse(Sole.getLezioniSeguite().contains(corsoBase.getLezioni().getFirst()));
        assertTrue(l1.getElencoTurniDisponibili().contains(ts));
        assertNull(Sole.getTurnoCorrente());

        // Adesso settando come turnoCorrente di sole un turno scaduto ci aspettiamo che venga restituito false
        Turno turnoScaduto = new Turno(10, LocalDate.now().minusDays(1), LocalTime.of(9, 0), LocalTime.of(11, 0));
        Sole.setTurnoCorrente(turnoScaduto);
        assertFalse(PB.disdiciTurno());

        // Verifichiamo ora che se sole ha prenotato un turno per l'ultima lezione del corsoBase (completandolo per il sistema)
        PB.selezionaTurno(l1.getElencoTurniDisponibili().getFirst());
        PB.selezionaTurno(tl2);
        assertFalse(Sole.getAttualmenteIscritto());
        assertNull(Sole.getCorsoCorrente());
        assertEquals(Sole.getLezioniSeguite().getLast(), l2);


        // Chiamando il metodo disdiciTurno
        PB.disdiciTurno();
        // Ci aspettiamo che il corsoCorrente torni ad essere il corsoBase
        assertEquals(Sole.getCorsoCorrente(), corsoBase);
        // Ci aspettiamo che l'attributo attualmenteIscritto di Sole sia true
        assertTrue(Sole.getAttualmenteIscritto());
        // Ci aspettiamo che l'ultima lezione seguita sia l1
        assertEquals(Sole.getLezioniSeguite().getLast(), l1);

    }   


    /* Test dell'estensione 3d dell'UC2 */
    @Test
    void testRecuperaLezione(){
        // Prenotiamo Walker di Giuseppe ad un turno della prima lezione del CorsoBase
        Lezione l1 = corsoBase.getLezioni().getFirst();
        Turno t1 = l1.getElencoTurniDisponibili().getFirst();
        // Settiamo come turnoCorrente di Walker ad un turno scaduto
        Turno turnoScaduto = new Turno(10, LocalDate.now().minusDays(1), LocalTime.of(9, 0), LocalTime.of(11, 0));
        Walker.setTurnoCorrente(turnoScaduto);
        // Aggiungiamo l1 alla lista delle lezioniSeguite di Walker
        Walker.getLezioniSeguite().add(l1);

    
        LinkedList<Turno> expected = l1.getTurniDisponibili();
        PB.selezionaCane(Walker);

        // Test del metodo 
        // Chiamando il metodo recuperaLezione
        // Ci aspettiamo che venga restituito l'elencoTurniDisponibili dell'ultima lezione seguita da Walker
        assertEquals(expected, PB.recuperaLezione());

        // Resettiamo lo stato iniziale
        Walker.setTurnoCorrente(null);
        Walker.getLezioniSeguite().clear();

        // Adesso prenotando walker ad un turno della prima lezione del corsoBase e invocando nuovamente il metodo sotto test
        // Ci aspettiamo che venga restituito null in quanto il turno non è scaduto
        PB.selezionaTurno(t1);
        assertEquals(Walker.getTurnoCorrente(), t1);
        assertNull(PB.recuperaLezione());

        // Resettiamo lo stato iniziale
        PB.disdiciTurno();

    }

    /* Test dell'estensione 3d dell'UC2 */
    @Test
    void testSelezionaTurnoRecupero(){
        // Prenotiamo Walker di Giuseppe ad un turno della prima lezione del CorsoBase
        Lezione l1 = corsoBase.getLezioni().getFirst();
        // Settiamo come turnoCorrente di Walker ad un turno scaduto
        Turno turnoScaduto = new Turno(10, LocalDate.now().minusDays(1), LocalTime.of(9, 0), LocalTime.of(11, 0));
        Walker.setTurnoCorrente(turnoScaduto);
        // Aggiungiamo l1 alla lista delle lezioniSeguite di Walker
        Walker.getLezioniSeguite().add(l1);
        PB.selezionaCane(Walker);

        LinkedList<Turno> turniDisponibili = PB.recuperaLezione();
        Turno turnoRecupero = turniDisponibili.getFirst();
        Turno turnoTestSovrapposizione = new Turno(12, LocalDate.now().plusWeeks(2), LocalTime.of(10, 0), LocalTime.of(10, 0));
        assertTrue(turniDisponibili.contains(turnoRecupero));
        assertFalse(Walker.getLezioneDaRecuperare());
        assertNotNull(Walker.getAffidoCorrente());
        //Test del metodo
        // Chiamando il metodo selezionaTurnoRecupero passando turnoTestSovrapposizione
        // Ci aspettiamo che venga restituito false
        assertFalse(PB.selezionaTurnoRecupero(turnoTestSovrapposizione));
        // Chiamando il metodo selezionaTurnoRecupero passando turnoRecupero
        // Ci aspettiamo venga restituito true
        // Ci aspettiamo che l'attributo lezioneDaRecuperare di Walker diventi true
        // Ci aspettiamo che turnoRecupero non appartenga più all'elencoTurniDisponibili di l1
        // Ci aspettiamo che turnoCorrente di Walker sia uguale a turnoRecupero
        assertTrue(PB.selezionaTurnoRecupero(turnoRecupero));
        assertTrue(Walker.getLezioneDaRecuperare());
        assertFalse(turniDisponibili.contains(turnoRecupero));
        assertEquals(turnoRecupero, Walker.getTurnoCorrente());
        
    }



}
