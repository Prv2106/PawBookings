package com.example;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * Unit tests for customer dog management, notification subscriptions, and date-overlap
 * checks.
 */
public class ClienteTest {
    public static PawBookings PB;

    @BeforeAll
    public static void initTest(){
        PB = PawBookings.getInstance();
        PB.registrati("Giuseppe", "Leocata", "48292478924", "0000");
        PB.aggiungiCane("Garibaldi", "Schanuzer");
        PB.aggiungiCane("Einstein", "Pastore Tedesco");
        PB.logout();
    }
    @Test
    void testCalcolaCaniNonInAffido(){
        // Verifies that the calculateCaniNonInAffido() method returns the list of dogs not in foster care.
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        // Selects a foster period and confirms Garibaldi's foster care.
        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(0));
        PB.confermaAffido(Giuseppe.getCane(1));

        // Verifies that the list contains only the dog not in foster care.
        LinkedList<Cane> caniNonInAffido = Giuseppe.calcolaCaniNonInAffido();
        assertEquals(Giuseppe.getCani().size() - 1, caniNonInAffido.size());
        // Verifies that the list contains Einstein the dog.
        Cane Einstein = Giuseppe.getCane(2);
        assertTrue(Giuseppe.calcolaCaniNonInAffido().contains(Einstein));
        // Verifies that the list does not contain the dog Garibaldi.
        Cane Garibaldi = Giuseppe.getCane(1);
        assertFalse(Giuseppe.calcolaCaniNonInAffido().contains(Garibaldi));
    }

    @Test
    void testRegistraCane(){
        // Verifies that the dog is added to the list of dogs owned by the customer.
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        int lunghezzaPrecedente = Giuseppe.getCani().size();

        // Adds a dog and verifies that the list contains one more dog.
        Giuseppe.registraCane("Cleopatra", "Cirneco", 3);
        assertEquals(lunghezzaPrecedente + 1, Giuseppe.getCani().size());
    }

    @Test
    void testRimuoviCane(){
        // Verifies that the dog is removed from the list of dogs owned by the customer.
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");
        int lunghezzaPrecedente = Giuseppe.getCani().size();
        PB.aggiungiCane("Betta", "Pastore del caucaso");

        // Verifies that the list contains one more dog.
        assertEquals(lunghezzaPrecedente + 1, Giuseppe.getCani().size());

        // Removes a dog and verifies that the list contains one fewer dog.
        Giuseppe.rimuoviCane(Giuseppe.getCani().getLast());
        assertEquals(lunghezzaPrecedente, Giuseppe.getCani().size());
    }

    @Test
    void testIscrizioneNotificheStatoSalute(){
        // Verifies that the customer has been added to the observer list.
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        PeriodoAffido p1 = PB.getPeriodiAffido().getFirst();
        // Verifies that the observer list is empty.
        assertEquals(0, p1.getObservers().size());

        // Registers the customer and verifies that the observer list contains one element.
        Giuseppe.iscrizioneNotificheStatoSalute(p1);
        assertEquals(1, p1.getObservers().size());
    }

    @Test
    void testAnnullamentoIscrizione(){
        // Verifies that the customer has been removed from the observer list.
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        PeriodoAffido p2 = PB.getPeriodiAffido().get(1);
        Giuseppe.iscrizioneNotificheStatoSalute(p2);
        assertEquals(1, p2.getObservers().size());

        // Unsubscribes and verifies that the observer list is empty.
        Giuseppe.annullamentoIscrizione(p2);
        assertEquals(0, p2.getObservers().size());
    }

    @Test
    void testResettaStatoSaluteCane(){
        // Verifies that resettaStatoSaluteCani() clears statoSaluteCani.
        LinkedList<Map<String, String>> mappaStatoSaluteCani = new LinkedList<>();
        Map<String, String> mappaStatoSalute = new HashMap<>();
        mappaStatoSalute.put("Garibaldi", "In ottima forma");
        mappaStatoSalute.put("Einstein", "In calore");
        mappaStatoSaluteCani.add(mappaStatoSalute);
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        // Verifies that the list contains an element.
        Giuseppe.setStatoSaluteCani(mappaStatoSaluteCani);
        assertEquals(1, mappaStatoSaluteCani.size());
        // Verifies that the list has been emptied.
        Giuseppe.resettaStatoSaluteCani();
        assertEquals(0, mappaStatoSaluteCani.size());
    }

    @Test
    void testUpdate(){
        // Verifies that update() adds an entry to the health-status list.
        Map<Integer, String> mappaStatoSalute = new HashMap<>();
        Map<String, String> expectedMappaStatoSaluteCani = new HashMap<>();
        LinkedList<Map<String, String>> expectedList = new LinkedList<>();
        mappaStatoSalute.put(1, "In ottima forma");
        expectedMappaStatoSaluteCani.put("Garibaldi", "In ottima forma");
        expectedList.add(expectedMappaStatoSaluteCani);

        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");
        PeriodoAffido p2 = PB.getPeriodiAffido().get(1);
        Giuseppe.iscrizioneNotificheStatoSalute(p2);
        p2.setMappaStatoSalute(mappaStatoSalute);
        p2.notifyObservers();

        // Verifies that the list contains an element.
        assertEquals(expectedList, Giuseppe.getStatoSalute());
    }

    @Test
    void testCheckSovrapposizioneDate(){
        Cliente Alberto = new Cliente("Alberto11", "ALberto", "Provenzano", "0000", "123456");
        // Shift overlaps the start date of periodoTest.
        Turno turnoTest1 = new Turno(16, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12,0));
        // Shift overlaps the end date of periodoTest.
        Turno turnoTest2 = new Turno(17, LocalDate.now().plusMonths(1), LocalTime.of(10, 0), LocalTime.of(12,0));
        // Shift falls inside periodoTest.
        Turno turnoTest3 = new Turno(18, LocalDate.now().plusWeeks(1), LocalTime.of(10, 0), LocalTime.of(12,0));
        // Shift does not overlap periodoTest.
        Turno turnoTest4 = new Turno(19, LocalDate.now().plusMonths(2), LocalTime.of(10, 0), LocalTime.of(12,0));
        PeriodoAffido periodoTest = new PeriodoAffido(1, LocalDate.now(), LocalDate.now().plusMonths(1), 200.0F);

        Cane Dog = new Cane(10, "Dog", "razza");

        // With the dog's current shift null, checkSovrapposizioneDate returns true.
        Dog.setTurnoCorrente(null);
        assertTrue(Alberto.checkSovrapposizioneDate(Dog, periodoTest));
        // With the current shift set to turnoTest1, checkSovrapposizioneDate returns false.
        Dog.setTurnoCorrente(turnoTest1);
        assertFalse(Alberto.checkSovrapposizioneDate(Dog, periodoTest));
        // With the current shift set to turnoTest2, checkSovrapposizioneDate returns false.
        Dog.setTurnoCorrente(turnoTest2);
        assertFalse(Alberto.checkSovrapposizioneDate(Dog, periodoTest));
        // With the current shift set to turnoTest3, checkSovrapposizioneDate returns false.
        Dog.setTurnoCorrente(turnoTest3);
        assertFalse(Alberto.checkSovrapposizioneDate(Dog, periodoTest));
        // With the current shift set to turnoTest4, checkSovrapposizioneDate returns true.
        Dog.setTurnoCorrente(turnoTest4);
        assertTrue(Alberto.checkSovrapposizioneDate(Dog, periodoTest));

    }
}
