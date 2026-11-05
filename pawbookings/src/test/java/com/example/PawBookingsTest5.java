package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;

/**
 * Integration tests for alternative flows of UC1-UC4 and the basic UC14 check-in flow.
 */
public class PawBookingsTest5 {
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

        // Adds shifts.
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
        Stella = PB.getClienteLoggato().getCane(1);
        PB.setCaneSelezionato(Stella);
        PB.confermaIscrizioneCorso(corsoBase);

        // Enrolls Maya in the basic course.
        Maya = PB.getClienteLoggato().getCane(3);
        PB.setCaneSelezionato(Maya);
        PB.confermaIscrizioneCorso(corsoBase);

        // Enrolls Maya in the basic course.
        Leila = PB.getClienteLoggato().getCane(4);
        PB.selezionaPeriodo(PB.getPeriodiAffido().getLast());
        PB.confermaAffido(Leila);

        // Places Stella in foster care in period 1.
        PB.selezionaPeriodo(PB.getPeriodiAffido().getFirst());
        PB.confermaAffido(Stella);

        // Ensures Duchessa completed every course.
        Duchessa = PB.getClienteLoggato().getCane(5);
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(0));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(1));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(2));

        // Enrolls Walker in the basic course.
        PB.accedi("Giuseppe2", "0000");
        Walker = PB.getClienteLoggato().getCane(6);
        PB.setCaneSelezionato(Walker);
        PB.confermaIscrizioneCorso(corsoBase);

        // Fills period 2.
        periodo2 = PB.getPeriodiAffido().get(1);
        periodo2.setNumPosti(0);
    }

    @Test
    void testNuovaIscrizioneCorso(){
        // Tests UC1, extension 3a.
        // Stella is already enrolled, so nuovaIscrizioneCorso must return null.
        // Expected: null.
        PB.selezionaCane(Stella);
        assertEquals(null, PB.nuovaIscrizioneCorso());

        // Tests UC1, extension 3b.
        // Duchessa completed every course, so nuovaIscrizioneCorso must return null.
        // Expected: null.
        PB.selezionaCane(Duchessa);
        assertEquals(null, PB.nuovaIscrizioneCorso());
    }

    @Test
    void testConfermaIscrizioneCorso(){
        PB.accedi("Alberto1", "0000");
        // Asso is not enrolled in any courses and has not completed any courses yet.
        Cane Asso = PB.getClienteLoggato().getCane(2);
        PB.selezionaCane(Asso);

        // Tests UC1, extension 5a.
        // Enrolls the dog in the advanced course.
        // Expected: false because the basic course is not completed.
        // Expected: the dog is not enrolled in any course.
        assertFalse(PB.confermaIscrizioneCorso(corsoAvanzato));
        assertFalse(Asso.getAttualmenteIscritto());

    }

    @Test
    void testPrenotaTurnoLezione(){
        PB.accedi("Alberto1", "0000");
        // Tests UC2, extension 3a.
        // Asso is not enrolled in any courses and has not completed any courses yet.
        Cane Asso = PB.getClienteLoggato().getCane(2);
        PB.selezionaCane(Asso);

        // Calling prenotaTurnoLezione with Asso returns null.
        assertEquals(null, PB.prenotaTurnoLezione());

        PB.selezionaCane(Stella);
        // The same call with enrolled Stella returns a non-null value.
        assertNotEquals(null, PB.prenotaTurnoLezione());

        // Tests UC2, extension 3b.
        // With a non-null current shift, prenotaTurnoLezione returns null.
        Turno t = new Turno(12, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12,0));
        Stella.setTurnoCorrente(t);
        assertEquals(null, PB.prenotaTurnoLezione());

    }

    @Test
    void testSelezionaTurno(){
        Turno turnoLezioneCorsoBase = corsoBase.getLezioni().getFirst().getTurniDisponibili().getLast();
        // Tests UC2, extension 5b.
        // Stella's foster period overlaps lesson 1 shifts.
        // Expected: false.
        PB.selezionaCane(Stella);
        assertFalse(PB.selezionaTurno(turnoLezioneCorsoBase));
    }

    @Test
    void testConfermaAffido(){
        // Tests UC3, extension 6a.
        // Duchessa cannot be fostered in the full period 2.
        // Expected: false.
        PB.accedi("Alberto1", "0000");
        PB.selezionaPeriodo(periodo2);
        assertFalse(PB.confermaAffido(Duchessa));

        // Tests UC3, extension 6b.
        // Walker cannot be fostered in period 1 after booking the first lesson shift.
        // Expected: false.

        PB.accedi("Giuseppe2", "0000");
        PB.selezionaCane(Walker);
        Turno ts = corsoBase.getLezioni().getFirst().getTurniDisponibili().getFirst();
        PB.selezionaTurno(ts);
        assertEquals(ts, Walker.getTurnoCorrente());
        PB.selezionaPeriodo(PB.getPeriodiAffido().getFirst());
        assertFalse(PB.confermaAffido(Walker));

    }

    @Test
    void testDelega(){
        // Tests UC4, extension 1b.
        // After delega is called, the customer delegation code is non-zero.
        PB.accedi("Giuseppe2", "0000");
        assertEquals(0, PB.getClienteLoggato().getCodiceDelega());
        PB.delega();
        assertNotEquals(0, PB.getClienteLoggato().getCodiceDelega());
    }

    @Test
    void testConcludiAffidoDelega(){
        PeriodoAffido periodo3 = PB.getPeriodiAffido().getLast();
        // Tests UC4, extension 4c.
        // Leila is in foster care in period 3 for Alberto.
        assertEquals(Leila.getAffidoCorrente(), periodo3);
        assertTrue(periodo3.getCaniAffido().contains(Leila));
        // Logs in as Alberto and generates a delegation code.
        PB.accedi("Alberto1", "0000");
        Cliente Alberto = PB.getClienteLoggato();
        PB.delega();
        PB.logout();

        // An incorrect delegation code returns null.
        assertNull(PB.concludiAffidoDelega(13244, 4));

        // An incorrect dog code returns null.
        assertNull(PB.concludiAffidoDelega(Alberto.getCodiceDelega(), 421));

        // Two incorrect parameters return null.
        assertNull(PB.concludiAffidoDelega(122412342, 421));

        // concludiAffidoDelega is invoked with Alberto's code and Leila's code.
        // Expected: Leila is removed from period 3.
        PB.concludiAffidoDelega(Alberto.getCodiceDelega(), 4);
        PB.confermaConclusioneAffido();
        assertNotEquals(Leila.getAffidoCorrente(), periodo3);
        assertFalse(periodo3.getCaniAffido().contains(Leila));

    }

    @Test
    void testMostraStatoAvanzamentoCorso(){
        // Tests UC10, extension 2a.
        // Leila is not enrolled in any course.
        // Calls mostraStatoAvanzamentoCorso.
        // Expected: false.
        PB.selezionaCane(Leila);
        assertEquals(null,PB.mostraStatoAvanzamentoCorso());
    }

    @Test
    void testAccediComeAdmin(){
        // Tests UC4, extension 3a.
        // Enters the correct PIN, 1234.
        // Expected: accediComeAdmin returns true.
        assertTrue(PB.accediComeAdmin(1234));

        // Enters an incorrect PIN.
        // Expected: accediComeAdmin returns false.
        assertFalse(PB.accediComeAdmin(0232));
    }

    @Test
    void testConcludiAffido(){
        // Tests UC4, extension 4a.
        // Stella is in foster care in period 1.
        // An incorrect customer code returns null from concludiAffido.
        assertEquals(null, PB.concludiAffido("126", 1));
        // The selected dog is not in foster care, so the call returns null.
        assertEquals(null, PB.concludiAffido("Alberto1", 3));
        // Invalid parameters return null from concludiAffido.
        assertEquals(null, PB.concludiAffido("Alberto123232", 3));
        // Tests UC4, extension 4a.
        // Asso is not in foster care.
        // A dog not in foster care returns null from concludiAffido.
        assertEquals(null, PB.concludiAffido("Alberto1", 2));
    }

    @Test
    void testTimbraPrenotazioneTurno() {
        // Tests the basic UC14 flow.

        // Given a customer code and a dog code,
        // this method returns the dog's turnoCorrente,
        // which may be null or a Turno instance.
        // Invalid customer or dog codes also return null.

        // The test covers the three null cases and the successful case.
        // The successful case returns a Turno instance.

        // Two incorrect parameters return null.
        assertNull(PB.timbraPrenotazioneTurno("sjhKD", 199));

        Cliente cl = new Cliente("Cliente1", "Lupo", "Alberto", "0000", "0123456789");
        Cane cn = new Cane(1, "Dog", "barboncino");
        cl.addCane(cn);
        PB.addCliente(cl);

        // A valid customer code with an invalid dog code returns null.
        assertNull(PB.timbraPrenotazioneTurno("Cliente1", 199));

        // An invalid customer code with a valid dog code returns null.
        assertNull(PB.timbraPrenotazioneTurno("Cliente1987", 1));

        // A dog without bookings returns null.

        assertNull(PB.timbraPrenotazioneTurno("Cliente1", 1));

        // Simulates a booking.
        PB.setCaneSelezionato(cn);
        PB.confermaIscrizioneCorso(corsoBase);
        Turno turnoDisponibile = PB.prenotaTurnoLezione().getLast();
        PB.selezionaTurno(turnoDisponibile);

        // The method returns the booked shift.
        assertEquals(turnoDisponibile.getCodice() , PB.timbraPrenotazioneTurno("Cliente1", 1).getCodice());
    }

    @Test
    void testConfermaTimbroTurno() {
        // Tests the basic UC14 flow.

        // Clears the dog's current shift reference.

        // Reuses the previous test to create a booking.

        testTimbraPrenotazioneTurno();

        // Verifies that the dog's current shift is not null.
        assertNotNull(PB.getCaneSelezionato().getTurnoCorrente());

        PB.confermaTimbroTurno();

         // Verifies that the dog's current shift is null.
         assertNull(PB.getCaneSelezionato().getTurnoCorrente());
    }

}
