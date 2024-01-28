package com.example;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;


/*
 * Test dei casi d'uso UC7-UC8
 */



class PawBookingsTest3 {
    static PawBookings PB;

    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings
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
        // test del metodo
        int lunghezzaIniziale = PB.getCorsi().size();
        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        Corso corsoBase = PB.getCorsi().getLast();
       
        // Ci aspettiamo che la lunghezza dell'elencoCorsi di PB adesso sia aumentata di 1
        assertEquals(lunghezzaIniziale+1, PB.getCorsi().size());

        // Ci aspettiamo che l'elencoCorsi di PB contenga corsoBase
        assertTrue(PB.getCorsi().contains(corsoBase));

    }


    @Test
    void testGeneraCodiceCorso(){
        // Inseriamo un nuovo corso 
        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);

        // Se chiamiamo il metodo generaCodiceCorso ci aspettiamo che venga restituito
        // un numero pari al numero di corsi + 1
        assertEquals(PB.getCorsi().size()+1, PB.generaCodiceCorso());
    }



    @Test
    void testSelezionaCorso(){
        PB.inserisciNuovoCorso("Corso Agility", 10, 300.0F);
        Corso corsoAgility = PB.getCorsi().getLast();

        // test del metodo 
        PB.selezionaCorso(corsoAgility);
        // Ci aspettiamo che corso selezionato sia corsoAgility
        assertEquals(corsoAgility, PB.getCorsoSelezionato());
    }


    @Test
    void testSetCorsoSelezionato(){
        PB.inserisciNuovoCorso("Corso NewAgility", 10, 400.0F);
        Corso corsoNewAgility = PB.getCorsi().getLast();

        PB.setCorsoSelezionato(corsoNewAgility);
        // Ci aspettiamo che il corso selezionato sia il corsoNewAgility
        assertEquals(corsoNewAgility, PB.getCorsoSelezionato());
        // Ci aspettiamo che il valore restituito da setCorsoSelezionato sia true
        assertTrue(PB.setCorsoSelezionato(corsoNewAgility));

        PB.setCorsoSelezionato(null);
        // Ci aspettiamo che il corso selezionato sia null
        assertEquals(null, PB.getCorsoSelezionato());
        // Ci aspettiamo che il valore restituito da setCorsoSelezionato sia false
        assertFalse(PB.setCorsoSelezionato(null));


    }



    @Test
    void testModificaCorso(){
        PB.inserisciNuovoCorso("Corso Test", 10, 400.0F);
        Corso corsoTest = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTest);

        // test del metodo
        PB.modificaCorso(5, 100.0F);

        // Ci aspettiamo che adesso la capienza sia 5 e che il costo sia 100.0F
        assertEquals(5, corsoTest.getCapienza());
        assertEquals(100.0F, corsoTest.getCosto());
    }



    @Test
    void testNuovaLezione(){
        PB.inserisciNuovoCorso("Corso TestLezione", 10, 300.0F);
        Corso corsoTestLezione = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTestLezione);

        // Settiamo a null la lezione Corrente
        corsoTestLezione.setLezioneCorrente(null);

        // Test del metodo
        PB.nuovaLezione("Lezione test");

        // Verifichiamo che è stata creata una nuova lezione
        assertNotEquals(null, corsoTestLezione.getLezioneCorrente());


    }


    @Test
    void generaCodiceLezione(){

    }
    
    
}
