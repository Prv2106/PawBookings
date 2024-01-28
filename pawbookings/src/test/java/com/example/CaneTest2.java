package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;



/*
 * Test dei casi d'uso UC3-UC4-UC5-UC6-UC7-UC8-UC9-UC10-UC11-UC12-UC13
 */

public class CaneTest2 {
    static PawBookings PB;
    @BeforeAll
    public static void initTest(){
        //Configurazioni che ci servono prima dell'esecuzione dei metodi di test
        PB = PawBookings.getInstance();
        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        Corso corsoBase = PB.modificaProgrammaCorso().get(0);
        PB.selezionaCorso(corsoBase);

        // Il codice di questa lezione sarà 1
        PB.nuovaLezione("Comandi di Base");
        PB.inserisciEsercizio("Saluto amichevole", "Questo esercizio aiuta a promuovere una socializzazione positiva tra cani");
        PB.inserisciEsercizio("Resta", "'Resta'per rimanere in posizione, aumentando gradualmente la distanza dal proprietario");
        PB.inserisciEsercizio("Richiamo", "Insegna al cane a rispondere al comando di richiamo del padrone");
        PB.confermaLezione();

        // Il codice di questa lezione sarà 2
        PB.nuovaLezione("Guinzaglio e Camminare al Guinzaglio");
        PB.inserisciEsercizio("Introduzione al Guinzaglio", "Insegnare ai proprietari come presentare il guinzaglio al cane in modo positivo, facendolo abituare gradualmente alla sensazione e premiando il comportamento calmo e collaborativo.");
        PB.inserisciEsercizio("Camminata Focalizzata", "Praticare una camminata controllata in cui il cane cammina al fianco del proprietario senza tirare al guinzaglio. Utilizzare comandi verbali e premi per incoraggiare un comportamento desiderato.");
        PB.inserisciEsercizio("Affrontare Distrazioni", "Introdurre gradualmente distrazioni durante la camminata al guinzaglio, come altri cani o stimoli ambientali. Insegnare ai proprietari come gestire le situazioni, mantenendo il controllo del cane e premiando il comportamento desiderato in presenza di distrazioni.");
        PB.confermaLezione();

        // Inserimento turni
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));

        PB.selezionaCane(new Cane(10, "Luna", "Barboncino"));
        PB.getCaneSelezionato().aggiornaAttualmenteIscritto(PB.getCorsi().get(0));
    }

    @Test
    void testAggiornaAttualmenteInAffido(){
        Cane cane = new Cane(11, "Sole", "Labrador");
        assertFalse(cane.getAttualmenteInAffido());
        assertEquals(null, cane.getAffidoCorrente());

        cane.aggiornaAttualmenteInAffido(PB.getPeriodiDisponibili().get(1));
        assertTrue(cane.getAttualmenteInAffido());
    }

    @Test
    void testConclusioneAffido(){
        Cane cane = new Cane(12, "Ginevra", "Belgian Malinois");
        cane.aggiornaAttualmenteInAffido(PB.getPeriodiDisponibili().get(2));
        assertTrue(cane.getAttualmenteInAffido());
        assertEquals(PB.getPeriodiDisponibili().get(2), cane.getAffidoCorrente());

        cane.concludiAffido();
        assertFalse(cane.getAttualmenteInAffido());
        assertEquals(null, cane.getAffidoCorrente());
    }
}
