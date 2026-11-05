package com.example;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.Lezione;
import domain_layer.PawBookings;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Integration tests for course and lesson administration (UC7-UC8).
 */
class PawBookingsTest3 {
    static PawBookings PB;

    // Runs before all tests.
    // Initializes the PawBookings instance.
    @BeforeAll
    public static void initTest() {
        PB = PawBookings.getInstance();
        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.logout();

       }

    @Test
    void testInserisciNuovoCorso(){
        int lunghezzaIniziale = PB.getCorsi().size();
        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        Corso corsoBase = PB.getCorsi().getLast();

        // Expected: the PB course list grows by one.
        assertEquals(lunghezzaIniziale+1, PB.getCorsi().size());

        // Expected: the PB course list contains the basic course.
        assertTrue(PB.getCorsi().contains(corsoBase));

    }

    @Test
    void testGeneraCodiceCorso(){
        // Inserts a new course.
        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);

        // Calling generaCodiceCorso returns
        // the number of courses plus one.
        assertEquals(PB.getCorsi().size()+1, PB.generaCodiceCorso());
    }

    @Test
    void testSelezionaCorso(){
        PB.inserisciNuovoCorso("Corso Agility", 10, 300.0F);
        Corso corsoAgility = PB.getCorsi().getLast();

        PB.selezionaCorso(corsoAgility);
        // Expected: the selected course is the agility course.
        assertEquals(corsoAgility, PB.getCorsoSelezionato());
    }

    @Test
    void testSetCorsoSelezionato(){
        PB.inserisciNuovoCorso("Corso NewAgility", 10, 400.0F);
        Corso corsoNewAgility = PB.getCorsi().getLast();

        PB.setCorsoSelezionato(corsoNewAgility);
        // Expected: the selected course is corsoNewAgility.
        assertEquals(corsoNewAgility, PB.getCorsoSelezionato());
        // Expected: setCorsoSelezionato returns true.
        assertTrue(PB.setCorsoSelezionato(corsoNewAgility));

        PB.setCorsoSelezionato(null);
        // Expected: the selected course is null.
        assertEquals(null, PB.getCorsoSelezionato());
        // Expected: setCorsoSelezionato returns false.
        assertFalse(PB.setCorsoSelezionato(null));

    }

    @Test
    void testModificaCorso(){
        PB.inserisciNuovoCorso("Corso Test", 10, 400.0F);
        Corso corsoTest = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTest);

        PB.modificaCorso(5, 100.0F);

        // Expected: capacity 5 and cost 100.0F.
        assertEquals(5, corsoTest.getCapienza());
        assertEquals(100.0F, corsoTest.getCosto());
    }

    @Test
    void testNuovaLezione(){
        PB.inserisciNuovoCorso("Corso TestLezione", 10, 300.0F);
        Corso corsoTestLezione = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTestLezione);

        // Sets lezioneCorrente to null.
        corsoTestLezione.setLezioneCorrente(null);

        PB.nuovaLezione("Lezione test");

        // Verifies that a new lesson was created.
        assertNotEquals(null, corsoTestLezione.getLezioneCorrente());

        // Resets the starting conditions.
        corsoTestLezione.setLezioneCorrente(null);
        PB.selezionaCorso(null);
    }

    @Test
    void generaCodiceLezione(){
        int expected;
        int numLezioni=0;

        for(Corso c: PB.getCorsi()){
            numLezioni += c.getLezioni().size();
        }
        expected = numLezioni +1;
        // Expected: the code equals the number of lessons plus one.
        assertEquals(expected, PB.generaCodiceLezione());

    }

    @Test
    void testInserisciEsercizio(){
        PB.inserisciNuovoCorso("Corso Test Esercizio", 10, 400.0F);
        Corso corsoTest = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTest);
        PB.nuovaLezione("Lezione test");
        PB.inserisciEsercizio("Esercizio test", "esercizio fittizio per eseguire il test del metodo");
        // Expected: the test lesson contains one exercise.
        assertEquals(1, corsoTest.getLezioneCorrente().getEsercizi().size());
        PB.inserisciEsercizio("Esercizio test2", "esercizio fittizio per eseguire il test del metodo");
        // Expected: the test lesson contains two exercises.
        assertEquals(2, corsoTest.getLezioneCorrente().getEsercizi().size());

    }

    @Test
    void testConfermaLezione(){
        PB.inserisciNuovoCorso("Corso Test Conferma Lezione", 10, 400.0F);
        Corso corsoTest = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTest);
        PB.nuovaLezione("Lezione test");
        PB.inserisciEsercizio("Esercizio test", "esercizio fittizio per eseguire il test del metodo");
        Lezione nuovaLezione = corsoTest.getLezioneCorrente();
        int lunghezzaPrecedente = corsoTest.getLezioni().size();
        PB.confermaLezione();

        // Expected: nuovaLezione is included in the corsoTest programme.
        assertTrue(corsoTest.getLezioni().contains(nuovaLezione));

        // Expected: the corsoTest programme length increases by one.
        assertEquals(lunghezzaPrecedente + 1, corsoTest.getLezioni().size());

    }

    @Test
    void testCalcolaCorsiConCaniIscritti(){
        PB.inserisciNuovoCorso("Corso Test Con Cane Iscritto", 10, 400.0F);
        Corso corsoTest1 = PB.getCorsi().getLast();
        corsoTest1.getCaniIscritti().add(new Cane(11, "Stella", "Pastore Tedesco"));
        PB.inserisciNuovoCorso("Corso Test senza Cani Iscritti", 10, 400.0F);
        Corso corsoTest2 = PB.getCorsi().getLast();
        // Expected: corsoTest2 is not present.
        assertFalse(PB.calcolaCorsiConCaniIscritti().contains(corsoTest2));

        // Expected: corsoTest1 is present.
        assertTrue(PB.calcolaCorsiConCaniIscritti().contains(corsoTest1));

    }

    @Test
    void testSelezionaLezione(){
        Lezione nuovaLezione = new Lezione(10, "Test");
        PB.inserisciNuovoCorso("Corso Test Seleziona Lezione", 10, 400.0F);
        Corso corsoTest = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTest);

        // Expected: the selected lesson equals the new lesson.
        PB.selezionaLezione(nuovaLezione);
        assertEquals(nuovaLezione,corsoTest.getLezioneSelezionata());
    }

    @Test
    void testVerificaDatiTurno(){
        // Valid shift data.
        assertTrue(PB.verificaDatiTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0)));
        // Invalid date.
        assertFalse(PB.verificaDatiTurno(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0)));
        // Invalid time range.
        assertFalse(PB.verificaDatiTurno(LocalDate.now().plusDays(1), LocalTime.of(19, 0), LocalTime.of(10, 0)));
        // Invalid date and time range.
        assertFalse(PB.verificaDatiTurno(LocalDate.now(), LocalTime.of(19, 0), LocalTime.of(10, 0)));

    }

    @Test
    void testNuovoTurno(){
        PB.inserisciNuovoCorso("Corso Test nuovo Turno", 10, 400.0F);
        Corso corsoTest = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTest);
        PB.nuovaLezione("Lezione test");
        PB.inserisciEsercizio("Esercizio test", "esercizio fittizio per eseguire il test del metodo");
        Lezione nuovaLezione = corsoTest.getLezioneCorrente();
        PB.confermaLezione();
        corsoTest.setLezioneSelezionata(nuovaLezione);
        int numeroTurniDisponibili = nuovaLezione.getTurniDisponibili().size();

        // Enters valid shift values.
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        // Expected: elencoTurniDisponibili grows by one.
        assertEquals(numeroTurniDisponibili+1, nuovaLezione.getTurniDisponibili().size());
        numeroTurniDisponibili = nuovaLezione.getTurniDisponibili().size();
        // Enters invalid shift values.
        PB.nuovoTurno(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0));
        // Expected: elencoTurniDisponibili is unchanged.
        assertEquals(numeroTurniDisponibili, nuovaLezione.getTurniDisponibili().size());

    }

}
