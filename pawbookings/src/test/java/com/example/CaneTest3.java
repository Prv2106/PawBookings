package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.Lezione;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;

/**
 * Unit tests for alternative enrollment, lesson calculation, and shift-eligibility
 * scenarios.
 */
class CaneTest3 {

    static LinkedList<Corso> elencoCorsiDisponibili = new LinkedList<>();
    static Corso corsoBase;
    static Corso corsoAvanzato;
    static Corso corsoAgility;
    static PawBookings PB;

    @BeforeAll
    public static void initTest() {

        corsoBase = new Corso(1, 10, 200.0F, "CorsoBase");
        corsoAvanzato = new Corso (2, 10, 200.0F, "CorsoAvanzato");
        corsoAgility = new Corso(3, 10, 200.0F, "CorsoAgility");
        elencoCorsiDisponibili.add(corsoBase);
        elencoCorsiDisponibili.add(corsoAvanzato);
        elencoCorsiDisponibili.add(corsoAgility);

        Lezione l1 = new Lezione(1, "lezione1");
        Lezione l2 = new Lezione(2, "lezione2");
        Lezione l3 = new Lezione(3, "lezione3");
        Lezione l4 = new Lezione(4, "lezione4");
        corsoBase.getLezioni().add(l1);
        corsoBase.getLezioni().add(l2);
        corsoAvanzato.getLezioni().add(l3);
        corsoAvanzato.getLezioni().add(l4);
    }

    @Test
    void testCheckNuovaIscrizioneCorso(){
        Cane caneTest = new Cane(1, "Dog", "Razza");
        caneTest.setAttualmenteIscritto(true);
        // Expected: false because attualmenteIscritto is true.
        assertFalse(caneTest.checkNuovaIscrizioneCorso(elencoCorsiDisponibili));
        caneTest.setAttualmenteIscritto(false);
        caneTest.getCorsiCompletati().add(corsoBase);
        caneTest.getCorsiCompletati().add(corsoAvanzato);
        caneTest.getCorsiCompletati().add(corsoAgility);
        // Expected: false because caneTest has completed every course.
        assertFalse(caneTest.checkNuovaIscrizioneCorso(elencoCorsiDisponibili));

        // Restores the initial conditions.
        caneTest.getCorsiCompletati().clear();
    }

    @Test
    void testCheckConfermaIscrizioneCorso(){
        Cane caneTest = new Cane(1, "Dog", "Razza");
        // caneTest has no completed course, so checkConfermaIscrizioneCorso(corsoAvanzato) must return false.
        // Expected: false.
        assertFalse(caneTest.checkConfermaIscrizioneCorso(corsoAvanzato));
        // caneTest has no completed course, so checkConfermaIscrizioneCorso(corsoBase) returns true.
        // Expected: true.
        assertTrue(caneTest.checkConfermaIscrizioneCorso(corsoBase));

        // Adds corsoBase to the caneTest completed courses.
        caneTest.getCorsiCompletati().add(corsoBase);
        // checkConfermaIscrizioneCorso(corsoBase) returns false.
        assertFalse(caneTest.checkConfermaIscrizioneCorso(corsoBase));
        // checkConfermaIscrizioneCorso(corsoAgility) returns false.
        assertFalse(caneTest.checkConfermaIscrizioneCorso(corsoAgility));
        // checkConfermaIscrizioneCorso(corsoAvanzato) returns true.
        assertTrue(caneTest.checkConfermaIscrizioneCorso(corsoAvanzato));

        // Restores the initial conditions.
        caneTest.getCorsiCompletati().clear();

    }

    @Test
    void testCheckSovrapposizioneDate(){
        Cane caneTest = new Cane(1, "Dog", "Razza");
        PeriodoAffido p1 = new PeriodoAffido(1, LocalDate.now(), LocalDate.now().plusMonths(1), 300.0F);
        PeriodoAffido p2 = new PeriodoAffido(1, LocalDate.now().plusWeeks(1), LocalDate.now().plusMonths(1), 300.0F);
        Turno t = new Turno(1, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0));
        // If the current foster period of caneTest is not set, true is returned.
        assertTrue(caneTest.checkSovrapposizioneDate(t));
        // Setting the current caneTest foster period to p1 returns false (because p1 overlaps with t)
        caneTest.setAffidoCorrente(p1);
        assertFalse(caneTest.checkSovrapposizioneDate(t));
        // Setting the current caneTest foster period to p2 returns true (because p2 is not overlapped with t)
        caneTest.setAffidoCorrente(p2);
        assertTrue(caneTest.checkSovrapposizioneDate(t));

    }

    @Test
    void testCalcolaLezioneSuccessiva(){
        // Verifies the following:
        // 1) The method returns the next lesson of the current course programme.
        // 2) After completing a course.
        // the method returns the first lesson of the new course.

        Cane Dog = new Cane(1, "Dog", "Razza");

        // If the dog is enrolled in the basic course, the next lesson is l1.
        assertEquals(corsoBase.getLezioni().getFirst(), Dog.calcolaLezioneSuccessiva(corsoBase.getLezioni()));
        // After adding l1 to the attended lessons, the next lesson is l2.
        Dog.getLezioniSeguite().add(corsoBase.getLezioni().getFirst());
        assertEquals(corsoBase.getLezioni().get(1), Dog.calcolaLezioneSuccessiva(corsoBase.getLezioni()));

        // After completing the basic course and enrolling in the advanced course, the next lesson is l3.
        Dog.getLezioniSeguite().add(corsoBase.getLezioni().get(1));
        assertEquals(corsoAvanzato.getLezioni().getFirst(), Dog.calcolaLezioneSuccessiva(corsoAvanzato.getLezioni()));
        // After adding l3 to the attended lessons, the next lesson is l4.
        Dog.getLezioniSeguite().add(corsoAvanzato.getLezioni().get(0));
        assertEquals(corsoAvanzato.getLezioni().get(1), Dog.calcolaLezioneSuccessiva(corsoAvanzato.getLezioni()));

    }

    @Test
    void testVerificaIdoneitaTurno(){
        Cane Dog = new Cane(1, "Dog", "razza");
        // Valid shift data.
        Turno t1 = new Turno(10, LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        Dog.setTurnoCorrente(t1);
        assertTrue(Dog.verificaIdoneitaTurno());

        // Invalid shift data.
        Turno t2 = new Turno(10, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0));
        Dog.setTurnoCorrente(t2);
        assertFalse(Dog.verificaIdoneitaTurno());
        // Missing shift.
        Dog.setTurnoCorrente(null);
        assertFalse(Dog.verificaIdoneitaTurno());

    }

}
