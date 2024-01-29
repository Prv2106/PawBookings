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

        // resettiamo le condizioni di partenza
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
        // test del metodo
        //Ci aspettiamo che il codice sia uguale al numero delle lezioni + 1
        assertEquals(expected, PB.generaCodiceLezione());

    }
    
    

    @Test
    void testInserisciEsercizio(){
        PB.inserisciNuovoCorso("Corso Test Esercizio", 10, 400.0F);
        Corso corsoTest = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTest);
        PB.nuovaLezione("Lezione test");
        // test del metodo 
        PB.inserisciEsercizio("Esercizio test", "esercizio fittizio per eseguire il test del metodo");
        // Ci aspettiamo che la lunghezza della lista di esercizi di Lezione test sia 1
        assertEquals(1, corsoTest.getLezioneCorrente().getEsercizi().size());
        PB.inserisciEsercizio("Esercizio test2", "esercizio fittizio per eseguire il test del metodo");
        // Ci aspettiamo che la lunghezza della lista di esercizi di Lezione test sia 2
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
        // Test del metodo
        PB.confermaLezione();

        // Ci aspettiamo che nuovaLezione sia stata inserita nel programma del corsoTest
        assertTrue(corsoTest.getLezioni().contains(nuovaLezione));

        // Ci aspettiamo che la lunghezza del programma del corsoTest sia aumentata di 1
        assertEquals(lunghezzaPrecedente + 1, corsoTest.getLezioni().size());

    }


    @Test
    void testCalcolaCorsiConCaniIscritti(){
        PB.inserisciNuovoCorso("Corso Test Con Cane Iscritto", 10, 400.0F);
        Corso corsoTest1 = PB.getCorsi().getLast();
        corsoTest1.getCaniIscritti().add(new Cane(11, "Stella", "Pastore Tedesco"));
        PB.inserisciNuovoCorso("Corso Test senza Cani Iscritti", 10, 400.0F);
        Corso corsoTest2 = PB.getCorsi().getLast();
        // Test del metodo
        // Ci aspettiamo che non sia Presente corsoTest2
        assertFalse(PB.calcolaCorsiConCaniIscritti().contains(corsoTest2));

        // Ci aspettiamo che  sia Presente corsoTest1
        assertTrue(PB.calcolaCorsiConCaniIscritti().contains(corsoTest1));

    }



    @Test
    void testSelezionaLezione(){
        Lezione nuovaLezione = new Lezione(10, "Test");
        PB.inserisciNuovoCorso("Corso Test Seleziona Lezione", 10, 400.0F);
        Corso corsoTest = PB.getCorsi().getLast();
        PB.selezionaCorso(corsoTest);

        // Ci aspettiamo che lezioneSelezionata sua uguale a nuovaLezione
        PB.selezionaLezione(nuovaLezione);
        assertEquals(nuovaLezione,corsoTest.getLezioneSelezionata());
    }



    @Test
    void testVerificaDatiTurno(){       
        // Turno Valido
        assertTrue(PB.verificaDatiTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0)));
        // Turno non valido per la data
        assertFalse(PB.verificaDatiTurno(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0)));
        // Turno non valido per l'ora
        assertFalse(PB.verificaDatiTurno(LocalDate.now().plusDays(1), LocalTime.of(19, 0), LocalTime.of(10, 0)));
        // Turno non valido per data e ora
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

        // test del metodo
        // Inserendo valori validi di turno
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        // Ci aspettiamo che la lunghezza dell'elencoTurniDisponibili di nuovaLezione sia aumentata di 1
        assertEquals(numeroTurniDisponibili+1, nuovaLezione.getTurniDisponibili().size());
        numeroTurniDisponibili = nuovaLezione.getTurniDisponibili().size();
        // Inserendo valori errati di turno
        PB.nuovoTurno(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0));
        // Ci aspettiamo che la lunghezza dell'elencoTurniDisponibili di nuovaLezione sia rimasta invariata
        assertEquals(numeroTurniDisponibili, nuovaLezione.getTurniDisponibili().size());


    }



}
