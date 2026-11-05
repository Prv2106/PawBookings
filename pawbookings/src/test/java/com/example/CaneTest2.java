package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;

import domain_layer.Corso;
import domain_layer.PawBookings;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;

/**
 * Unit tests for foster care, dog removal, and related dog operations (UC3-UC9 and UC11-UC13).
 */
public class CaneTest2 {
    static PawBookings PB;
    @BeforeAll
    public static void initTest(){
        // Initializes the fixtures required by the tests.
        PB = PawBookings.getInstance();
        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        Corso corsoBase = PB.modificaProgrammaCorso().get(0);
        PB.selezionaCorso(corsoBase);

        // The lesson code is 1.
        PB.nuovaLezione("Comandi di Base");
        PB.inserisciEsercizio("Saluto amichevole", "Questo esercizio aiuta a promuovere una socializzazione positiva tra cani");
        PB.inserisciEsercizio("Resta", "'Resta'per rimanere in posizione, aumentando gradualmente la distanza dal proprietario");
        PB.inserisciEsercizio("Richiamo", "Insegna al cane a rispondere al comando di richiamo del padrone");
        PB.confermaLezione();

        // The lesson code is 2.
        PB.nuovaLezione("Guinzaglio e Camminare al Guinzaglio");
        PB.inserisciEsercizio("Introduzione al Guinzaglio", "Insegnare ai proprietari come presentare il guinzaglio al cane in modo positivo, facendolo abituare gradualmente alla sensazione e premiando il comportamento calmo e collaborativo.");
        PB.inserisciEsercizio("Camminata Focalizzata", "Praticare una camminata controllata in cui il cane cammina al fianco del proprietario senza tirare al guinzaglio. Utilizzare comandi verbali e premi per incoraggiare un comportamento desiderato.");
        PB.inserisciEsercizio("Affrontare Distrazioni", "Introdurre gradualmente distrazioni durante la camminata al guinzaglio, come altri cani o stimoli ambientali. Insegnare ai proprietari come gestire le situazioni, mantenendo il controllo del cane e premiando il comportamento desiderato in presenza di distrazioni.");
        PB.confermaLezione();

        // Adds shifts.
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));

        PB.selezionaCane(new Cane(10, "Luna", "Barboncino"));
        PB.getCaneSelezionato().aggiornaAttualmenteIscritto(PB.getCorsi().get(0));

        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.logout();
    }

    @Test
    void testAggiornaAttualmenteInAffido(){
        // Verifies that the dog is added to the foster-dog list.
        Cane cane = new Cane(11, "Sole", "Labrador");
        // Verifies that the dog is not currently in foster care.
        assertFalse(cane.getAttualmenteInAffido());
        assertEquals(null, cane.getAffidoCorrente());

        // Updates the foster-care state.
        cane.aggiornaAttualmenteInAffido(PB.getPeriodiAffidoDisponibili().get(1));
        // Verifies that the dog is in foster care.
        assertTrue(cane.getAttualmenteInAffido());
    }

    @Test
    void testConclusioneAffido(){
        // Verifies that the dog is removed from the foster-dog list.
        Cane cane = new Cane(12, "Ginevra", "Belgian Malinois");
        cane.aggiornaAttualmenteInAffido(PB.getPeriodiAffidoDisponibili().get(1));
        // Verifies that the dog is in foster care.
        assertTrue(cane.getAttualmenteInAffido());
        cane.conclusioneAffido();
        // Verifies that the dog is no longer in foster care.
        assertFalse(cane.getAttualmenteInAffido());
        // Verifies that the dog is no longer in foster care.
        assertNull(cane.getAffidoCorrente());
    }

    @Test
    void testAggiornaAssociazioniCane(){
        // Verifies that the dog is removed from corsoCorrente and
        // from the course enrolled-dog list.

        PB.getClienti().get("Alberto1");
        // Logs in the customer.
        PB.accedi("Alberto1", "0000");

        Cane cane = PB.getClienteLoggato().getCane(1);
        // Enrolls the dog in the basic course.
        Corso c = PB.getCorsi().get(0);
        PB.selezionaCane(cane);
        PB.confermaIscrizioneCorso(c);
        // Verifies that the dog is enrolled in the course.
        assertTrue(cane.getAttualmenteIscritto());
        // Verifies that the dog appears in the course enrolled-dog list.
        assertTrue(c.getCaniIscritti().contains(cane));

        // Removes the dog from the course.
        PB.selezionaCane(cane);
        cane.aggiornaAssociazioniCane();
        // Verifies that the enrolled-dog list no longer contains the dog.
        assertFalse(c.getCaniIscritti().contains(cane));

    }

}

