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
        Corso corsoBase = PB.getCorsi().getFirst();
       
        // Ci aspettiamo che la lunghezza dell'elencoCorsi di PB adesso sia aumentata di 1
        assertEquals(lunghezzaIniziale+1, PB.getCorsi().size());

        // Ci aspettiamo che l'elencoCorsi di PB contenga corsoBase
        assertTrue(PB.getCorsi().contains(corsoBase));


        // test del metodo generaCodiceCorso
        // Ci aspettiamo che il codice di CorsoBase sia 1
        assertEquals(1, corsoBase.getCodice());
    }


    @Test
    void testGeneraCodiceCorso(){
        // Inseriamo un nuovo corso 
        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);

        // Se chiamiamo il metodo generaCodiceCorso ci aspettiamo che venga restituito
        // un numero pari al numero di corsi + 1
        assertEquals(PB.getCorsi().size()+1, PB.generaCodiceCorso());



    }


   




    
}
