package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
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
    
        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.logout();
    }

    @Test
    void testAggiornaAttualmenteInAffido(){
        // nel test verifichiamo che il cane venga aggiunto alla lista dei cani in affido
        Cane cane = new Cane(11, "Sole", "Labrador");
        // verifichiamo che il cane non sia attualmente in affido
        assertFalse(cane.getAttualmenteInAffido());
        assertEquals(null, cane.getAffidoCorrente());

        // aggiorniamo lo stato attualmente in affido
        cane.aggiornaAttualmenteInAffido(PB.getPeriodiAffidoDisponibili().get(1));
        // verifichiamo che il cane sia attualmente in affido
        assertTrue(cane.getAttualmenteInAffido());
    }

    @Test
    void testConclusioneAffido(){
        // nel test verifichiamo che il cane venga rimosso dalla lista dei cani in affido
        Cane cane = new Cane(12, "Ginevra", "Belgian Malinois");
        cane.aggiornaAttualmenteInAffido(PB.getPeriodiAffidoDisponibili().get(1));
        // verifichiamo che il cane sia attualmente in affido
        assertTrue(cane.getAttualmenteInAffido());
        cane.conclusioneAffido();
        // verifichiamo che il cane non sia più attualmente in affido
        assertFalse(cane.getAttualmenteInAffido());
        // verifichiamo che il cane non sia più in affido
        assertNull(cane.getAffidoCorrente());
    }

    @Test
    void testAggiornaAssociazioniCane(){
        // nel test verifichiamo che il cane venga rimosso dal corso corrente e
        // dalla lista dei cani iscritti al corso

        Cliente Alberto = PB.getClienti().get("Alberto1"); 
        // effettuiamo il login del cliente
        PB.accedi("Alberto1", "0000");
        
        Cane cane = PB.getClienteLoggato().getCane(1);
        // iscriviamo il cane al corso base
        Corso c = PB.getCorsi().get(0);
        PB.selezionaCane(cane);
        PB.confermaIscrizioneCorso(c);
        // verifichiamo che il cane sia iscritto al corso
        assertTrue(cane.getAttualmenteIscritto());
        // verifichiamo che il cane sia presente nella lista dei cani iscritti al corso
        assertTrue(c.getCaniIscritti().contains(cane));
        
        
        // rimuoviamo il cane dal corso
        PB.selezionaCane(cane);
        cane.aggiornaAssociazioniCane();
        // verifichiamo che l'elenco cani iscritti non contenga più il cane
        assertFalse(c.getCaniIscritti().contains(cane));

    }


}
