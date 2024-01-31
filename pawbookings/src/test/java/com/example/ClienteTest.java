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
        // verifichiamo che il metodo calcolaCaniNonInAffido() restituisca l'elenco dei cani non in affido
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        // selezioniamo un periodo di affido e confermiamo l'affido di Garibaldi
        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(0));
        PB.confermaAffido(Giuseppe.getCane(1));

        // verifichiamo che la lista contenga un solo cane(quello non in affido)
        LinkedList<Cane> caniNonInAffido = Giuseppe.calcolaCaniNonInAffido();
        assertEquals(Giuseppe.getCani().size() - 1, caniNonInAffido.size());
        // verifichiamo che la lista contenga il cane Einstein
        Cane Einstein = Giuseppe.getCane(2);
        assertTrue(Giuseppe.calcolaCaniNonInAffido().contains(Einstein));
        // verifichiamo che la lista non contenga il cane Garibaldi
        Cane Garibaldi = Giuseppe.getCane(1);
        assertFalse(Giuseppe.calcolaCaniNonInAffido().contains(Garibaldi));
    }

    @Test
    void testRegistraCane(){
        // verifichiamo che il cane venga aggiunto alla lista dei cani posseduti dal cliente
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        int lunghezzaPrecedente = Giuseppe.getCani().size();

        // aggiungiamo un cane e verifichiamo che la lista contenga un cane in più
        Giuseppe.registraCane("Cleopatra", "Cirneco", 3);
        assertEquals(lunghezzaPrecedente + 1, Giuseppe.getCani().size());
    }

    @Test
    void testRimuoviCane(){
        // verifichiamo che il cane venga rimosso dalla lista dei cani posseduti dal cliente
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");
        int lunghezzaPrecedente = Giuseppe.getCani().size();
        PB.aggiungiCane("Betta", "Pastore del caucaso");

        // verifichiamo che la lista contenga un cane in più
        assertEquals(lunghezzaPrecedente + 1, Giuseppe.getCani().size());

        // rimuoviamo un cane e verifichiamo che la lista contenga un cane in meno
        Giuseppe.rimuoviCane(Giuseppe.getCani().getLast());
        assertEquals(lunghezzaPrecedente, Giuseppe.getCani().size());
    }

    @Test
    void testIscrizioneNotificheStatoSalute(){
        // verifichiamo che il cliente sia stato aggiunto alla lista degli osservatori
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        PeriodoAffido p1 = PB.getPeriodiAffido().getFirst();
        // verifichiamo che la lista degli osservatori sia vuota
        assertEquals(0, p1.getObservers().size());

        // iscriviamo il cliente e verifichiamo che la lista degli osservatori contenga un solo elemento
        Giuseppe.iscrizioneNotificheStatoSalute(p1);
        assertEquals(1, p1.getObservers().size());
    }

    @Test
    void testAnnullamentoIscrizione(){
        // verifichiamo che il cliente sia stato eliminato alla lista degli osservatori
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        PeriodoAffido p2 = PB.getPeriodiAffido().get(1);
        Giuseppe.iscrizioneNotificheStatoSalute(p2);
        assertEquals(1, p2.getObservers().size());

        // annulliamo l'iscrizione e verifichiamo che la lista degli osservatori sia vuota
        Giuseppe.annullamentoIscrizione(p2);
        assertEquals(0, p2.getObservers().size());
    }

    @Test
    void testResettaStatoSaluteCane(){
        // verifichiamo che il metodo resettaStatoSaluteCani() svuoti la lista statoSaluteCani
        LinkedList<Map<String, String>> mappaStatoSaluteCani = new LinkedList<>();
        Map<String, String> mappaStatoSalute = new HashMap<>();
        mappaStatoSalute.put("Garibaldi", "In ottima forma");
        mappaStatoSalute.put("Einstein", "In calore");
        mappaStatoSaluteCani.add(mappaStatoSalute);
        Cliente Giuseppe = PB.getClienti().get("Giuseppe1");
        PB.accedi("Giuseppe1", "0000");

        // verifichiamo che la lista contenga un elemento
        Giuseppe.setStatoSaluteCani(mappaStatoSaluteCani);
        assertEquals(1, mappaStatoSaluteCani.size());
        // verifichiamo che la lista sia stata svuotata
        Giuseppe.resettaStatoSaluteCani();
        assertEquals(0, mappaStatoSaluteCani.size());
    }

    @Test
    void testUpdate(){
        // verifichiamo che il metodo update() aggiunga alla lista statoSaluteCani
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

        // verifichiamo che la lista contenga un elemento
        assertEquals(expectedList, Giuseppe.getStatoSalute());
    }


  

    @Test
    void testCheckSovrapposizioneDate(){
        Cliente Alberto = new Cliente("Alberto11", "ALberto", "Provenzano", "0000", "123456");
        // Turno sovrapposto con dataInizio di periodoTest
        Turno turnoTest1 = new Turno(16, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12,0));
        // Turno sovrapposto con dataFine di periodoTest
        Turno turnoTest2 = new Turno(17, LocalDate.now().plusMonths(1), LocalTime.of(10, 0), LocalTime.of(12,0));
        // Turno che si trova sovrapposto tra dataInizio e dataFine di periodoTest
        Turno turnoTest3 = new Turno(18, LocalDate.now().plusWeeks(1), LocalTime.of(10, 0), LocalTime.of(12,0));
        // Turno non sovrapposto a periodoTest
        Turno turnoTest4 = new Turno(19, LocalDate.now().plusMonths(2), LocalTime.of(10, 0), LocalTime.of(12,0));
        PeriodoAffido periodoTest = new PeriodoAffido(1, LocalDate.now(), LocalDate.now().plusMonths(1), 200.0F);

        Cane Dog = new Cane(10, "Dog", "razza");
       
        // chiamndo il checkSovrapposizioneDate settando il turnoCorrente di Dog a null ci aspettiamo che venga restituito true
        Dog.setTurnoCorrente(null);
        assertTrue(Alberto.checkSovrapposizioneDate(Dog, periodoTest));
        // chiamndo il checkSovrapposizioneDate settando il turnoCorrente di Dog a turnoTest1 ci aspettiamo che venga restituito false
        Dog.setTurnoCorrente(turnoTest1);
        assertFalse(Alberto.checkSovrapposizioneDate(Dog, periodoTest));  
        // chiamndo il checkSovrapposizioneDate settando il turnoCorrente di Dog a turnoTest2 ci aspettiamo che venga restituito false
        Dog.setTurnoCorrente(turnoTest2);
        assertFalse(Alberto.checkSovrapposizioneDate(Dog, periodoTest));   
        // chiamndo il checkSovrapposizioneDate settando il turnoCorrente di Dog a turnoTest3 ci aspettiamo che venga restituito false 
        Dog.setTurnoCorrente(turnoTest3);
        assertFalse(Alberto.checkSovrapposizioneDate(Dog, periodoTest));  
        // chiamndo il checkSovrapposizioneDate settando il turnoCorrente di Dog a turnoTest4 ci aspettiamo che venga restituito true
        Dog.setTurnoCorrente(turnoTest4);
        assertTrue(Alberto.checkSovrapposizioneDate(Dog, periodoTest)); 
        
    }
}
