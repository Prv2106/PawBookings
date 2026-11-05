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

/**
 * Integration tests for shift cancellation and lesson recovery (UC2 extensions 3c and
 * 3d).
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

    // Runs before all tests.
    // Initializes the PawBookings instance.

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

        // Adds four shifts to lesson 1 and two shifts to lesson 2.
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

        // Enrolls Stella in the basic course.
        PB.accedi("Alberto1", "0000");
        Alberto = PB.getClienteLoggato();
        Stella = Alberto.getCane(1);
        PB.setCaneSelezionato(Stella);
        PB.confermaIscrizioneCorso(corsoBase);

        // Enrolls Maya in the basic course.
        Maya = Alberto.getCane(3);
        PB.setCaneSelezionato(Maya);
        PB.confermaIscrizioneCorso(corsoBase);

        // Enrolls Maya in the basic course.
        Leila = Alberto.getCane(4);
        PB.selezionaPeriodo(PB.getPeriodiAffido().getLast());
        PB.confermaAffido(Leila);

        // Places Stella in foster care in period 1.
        PB.selezionaPeriodo(PB.getPeriodiAffido().getFirst());
        PB.confermaAffido(Stella);

        // Ensures Duchessa completed every course.
        Duchessa = Alberto.getCane(5);
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(0));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(1));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(2));

        // Enrolls Walker in the basic course.
        PB.accedi("Giuseppe2", "0000");
        Giuseppe = PB.getClienteLoggato();
        Walker = Giuseppe.getCane(6);
        PB.setCaneSelezionato(Walker);
        PB.confermaIscrizioneCorso(corsoBase);

        // Places Walker in foster care in period 2.
        PB.selezionaPeriodo(PB.getPeriodiAffido().get(1));
        PB.confermaAffido(Walker);

        // Enrolls Sole in the basic course.
        PB.accedi("Daniele3", "0000");
        Daniele = PB.getClienteLoggato();
        Sole = Daniele.getCani().getFirst();
        PB.setCaneSelezionato(Sole);
        PB.confermaIscrizioneCorso(corsoBase);
        PB.logout();

    }

    // Tests UC2, extension 3c.
    @Test
    void testDisdiciTurno(){
        // Books Daniele's dog Sole in the first basic-course shift.
        PB.selezionaCane(Sole);
        Turno ts = corsoBase.getLezioni().getFirst().getElencoTurniDisponibili().getFirst();
        Lezione l1 = corsoBase.getLezioni().getFirst();
        Lezione l2 =corsoBase.getLezioni().get(1);
        Turno tl2 = l2.getElencoTurniDisponibili().getFirst();

        PB.selezionaTurno(ts);
        // The first lesson is among Sole's attended lessons.
        assertTrue(Sole.getLezioniSeguite().contains(corsoBase.getLezioni().getFirst()));
        assertEquals(Sole.getTurnoCorrente(), ts);
        assertFalse(l1.getElencoTurniDisponibili().contains(ts));
        // Calls disdiciTurno.
        // Expected: Sole's current shift becomes null.
        // Expected: shift ts returns to l1's elencoTurniDisponibili.
        // Expected: the first lesson is removed from Sole's attended lessons.
        PB.disdiciTurno();
        assertFalse(Sole.getLezioniSeguite().contains(corsoBase.getLezioni().getFirst()));
        assertTrue(l1.getElencoTurniDisponibili().contains(ts));
        assertNull(Sole.getTurnoCorrente());

        // An expired current shift makes disdiciTurno return false.
        Turno turnoScaduto = new Turno(10, LocalDate.now().minusDays(1), LocalTime.of(9, 0), LocalTime.of(11, 0));
        Sole.setTurnoCorrente(turnoScaduto);
        assertFalse(PB.disdiciTurno());

        // Verifies the case in which Sole books the last lesson and completes the course.
        PB.selezionaTurno(l1.getElencoTurniDisponibili().getFirst());
        PB.selezionaTurno(tl2);
        assertFalse(Sole.getAttualmenteIscritto());
        assertNull(Sole.getCorsoCorrente());
        assertEquals(Sole.getLezioniSeguite().getLast(), l2);

        // Calls disdiciTurno.
        PB.disdiciTurno();
        // Expected: corsoCorrente returns to the basic course.
        assertEquals(Sole.getCorsoCorrente(), corsoBase);
        // Expected: Sole attualmenteIscritto is true.
        assertTrue(Sole.getAttualmenteIscritto());
        // Expected: the last attended lesson is l1.
        assertEquals(Sole.getLezioniSeguite().getLast(), l1);

    }

    // Tests UC2, extension 3d.
    @Test
    void testRecuperaLezione(){
        // Books Giuseppe's dog Walker in the first basic-course lesson.
        Lezione l1 = corsoBase.getLezioni().getFirst();
        Turno t1 = l1.getElencoTurniDisponibili().getFirst();
        // Sets Walker's current shift to an expired shift.
        Turno turnoScaduto = new Turno(10, LocalDate.now().minusDays(1), LocalTime.of(9, 0), LocalTime.of(11, 0));
        Walker.setTurnoCorrente(turnoScaduto);
        // Adds l1 to Walker's attended lessons.
        Walker.getLezioniSeguite().add(l1);

        LinkedList<Turno> expected = l1.getTurniDisponibili();
        PB.selezionaCane(Walker);

        // Calls recuperaLezione.
        // Expected: Walker's last-lesson available shifts are returned.
        assertEquals(expected, PB.recuperaLezione());

        // Resets the initial state.
        Walker.setTurnoCorrente(null);
        Walker.getLezioniSeguite().clear();

        // Books Walker in the first lesson and calls the method again.
        // Expected: null because the shift has not expired.
        PB.selezionaTurno(t1);
        assertEquals(Walker.getTurnoCorrente(), t1);
        assertNull(PB.recuperaLezione());

        // Resets the initial state.
        PB.disdiciTurno();

    }

    // Tests UC2, extension 3d.
    @Test
    void testSelezionaTurnoRecupero(){
        // Books Giuseppe's dog Walker in the first basic-course lesson.
        Lezione l1 = corsoBase.getLezioni().getFirst();
        // Sets Walker's current shift to an expired shift.
        Turno turnoScaduto = new Turno(10, LocalDate.now().minusDays(1), LocalTime.of(9, 0), LocalTime.of(11, 0));
        Walker.setTurnoCorrente(turnoScaduto);
        // Adds l1 to Walker's attended lessons.
        Walker.getLezioniSeguite().add(l1);
        PB.selezionaCane(Walker);

        LinkedList<Turno> turniDisponibili = PB.recuperaLezione();
        Turno turnoRecupero = turniDisponibili.getFirst();
        Turno turnoTestSovrapposizione = new Turno(12, LocalDate.now().plusWeeks(2), LocalTime.of(10, 0), LocalTime.of(10, 0));
        assertTrue(turniDisponibili.contains(turnoRecupero));
        assertFalse(Walker.getLezioneDaRecuperare());
        assertNotNull(Walker.getAffidoCorrente());
        // Calls selezionaTurnoRecupero with turnoTestSovrapposizione.
        // Expected: false.
        assertFalse(PB.selezionaTurnoRecupero(turnoTestSovrapposizione));
        // Calls selezionaTurnoRecupero with turnoRecupero.
        // Expected: true.
        // Expected: Walker's lezioneDaRecuperare becomes true.
        // Expected: turnoRecupero is removed from l1's elencoTurniDisponibili.
        // Expected: Walker's current shift is turnoRecupero.
        assertTrue(PB.selezionaTurnoRecupero(turnoRecupero));
        assertTrue(Walker.getLezioneDaRecuperare());
        assertFalse(turniDisponibili.contains(turnoRecupero));
        assertEquals(turnoRecupero, Walker.getTurnoCorrente());

    }

}
