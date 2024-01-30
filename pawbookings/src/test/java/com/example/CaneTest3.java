package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;

/*
 * Test degli scenari alternativi dei casi d'uso degli UC1-UC2-UC3-UC4
 */


class CaneTest3 {

  
    static LinkedList<Corso> elencoCorsiDisponibili = new LinkedList<>();
    static Corso corsoBase;
    static Corso corsoAvanzato;
    static Corso corsoAgility;

    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings

    @BeforeAll
    public static void initTest() {
        corsoBase = new Corso(1, 10, 200.0F, "CorsoBase");
        corsoAvanzato = new Corso (2, 10, 200.0F, "CorsoAvanzato");
        corsoAgility = new Corso(3, 10, 200.0F, "CorsoAgility");
        elencoCorsiDisponibili.add(corsoBase);
        elencoCorsiDisponibili.add(corsoAvanzato);
        elencoCorsiDisponibili.add(corsoAgility);
    }

    /*
     * if((attualmenteIscritto == true) || (corsiCompletati.containsAll(elencoCorsiDisponibili))){
            return false;
        }
        else{
            return true;
        }
     */
    @Test
    void testCheckNuovaIscrizioneCorso(){
        Cane caneTest = new Cane(1, "Dog", "Razza");
        caneTest.setAttualmenteIscritto(true);
        // Ci aspettiamo false perché attualmenteIscritto è true
        assertFalse(caneTest.checkNuovaIscrizioneCorso(elencoCorsiDisponibili));
        caneTest.setAttualmenteIscritto(false);
        caneTest.getCorsiCompletati().add(corsoBase);
        caneTest.getCorsiCompletati().add(corsoAvanzato);
        caneTest.getCorsiCompletati().add(corsoAgility);
        // Ci aspettiamo false perché caneTest ha completato tutti i corsi
        assertFalse(caneTest.checkNuovaIscrizioneCorso(elencoCorsiDisponibili));

        // Ripristino le condizioni iniziali
        caneTest.getCorsiCompletati().clear();
    }



    @Test
    void testCheckConfermaIscrizioneCorso(){
        Cane caneTest = new Cane(1, "Dog", "Razza");
        // caneTest non ha seguito alcun corso, chiamando il metodo checkConfermaIscrizioneCorso passando il corso avanzato
        // Ci aspettiamo che venga restituito false
        assertFalse(caneTest.checkConfermaIscrizioneCorso(corsoAvanzato));
        // caneTest non ha seguito alcun corso, chiamando il metodo checkConfermaIscrizioneCorso passando il corso base
        // Ci aspettiamo che venga restituito true
        assertTrue(caneTest.checkConfermaIscrizioneCorso(corsoBase));

        // Se tra i corsi completati di caneTest aggiungiamo corsoBase
        caneTest.getCorsiCompletati().add(corsoBase);
        // Provando a fare il check passando corsoBase ci viene restituito false
        assertFalse(caneTest.checkConfermaIscrizioneCorso(corsoBase));
        // Provando a fare il check passando corsoAgility ci viene restituito false
        assertFalse(caneTest.checkConfermaIscrizioneCorso(corsoAgility));
        // Provando a fare il check passando corsoAvanzato ci viene restituito true
        assertTrue(caneTest.checkConfermaIscrizioneCorso(corsoAvanzato));

        // Ripristino le condizioni iniziali
        caneTest.getCorsiCompletati().clear();       

    }

    @Test
    void testCheckSovrapposizioneDate(){
        Cane caneTest = new Cane(1, "Dog", "Razza");
        PeriodoAffido p1 = new PeriodoAffido(1, LocalDate.now(), LocalDate.now().plusMonths(1), 300.0F);
        PeriodoAffido p2 = new PeriodoAffido(1, LocalDate.now().plusWeeks(1), LocalDate.now().plusMonths(1), 300.0F);
        Turno t = new Turno(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0));
        // se non viene settato l'affidoCorrente di caneTest viene restituito true
        assertTrue(caneTest.checkSovrapposizioneDate(t));
        // settando l'affido corrente di caneTest a p1 viene restituito false (perchè p1 è sovrapposto a t)
        caneTest.setAffidoCorrente(p1);
        assertFalse(caneTest.checkSovrapposizioneDate(t));
        // settando l'affido corrente di caneTest a p2 viene restituito true (perchè p2 non è sovrapposto a t)
        caneTest.setAffidoCorrente(p2);
        assertTrue(caneTest.checkSovrapposizioneDate(t));


    }


}
