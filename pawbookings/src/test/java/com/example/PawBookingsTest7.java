package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.Lezione;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;


/*
 * Test degli scenari alternativi 3c e 3d dell UC2:Gestisci prenotazione turno lezione
 * Test delle regole di dominioR1-R2-R3-R4
 */


public class PawBookingsTest7 {
    static PawBookings PB;
    static Cane Stella;
    static Cane Duchessa;
    static Corso corsoBase;
    static Corso corsoAvanzato;
    static Corso corsoAgility;
    static Cane Maya;
    static Cane Leila;
    static Cliente Alberto;
    



    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings

    @BeforeAll
    public static void initTest() {
        PB = PawBookings.getInstance();
        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.aggiungiCane("Maya", "Pastore Tedesco");
        PB.aggiungiCane("Leila", "Labrador");
        PB.aggiungiCane("Duchessa", "Labrador");
        PB.logout();
        
    
        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        corsoBase = PB.modificaProgrammaCorso().get(0);
        PB.selezionaCorso(corsoBase);


        PB.nuovaLezione("Comandi di Base");
        PB.inserisciEsercizio("Saluto amichevole", "Questo esercizio aiuta a promuovere una socializzazione positiva tra cani");
        PB.confermaLezione();

        PB.nuovaLezione("Guinzaglio e Camminare al Guinzaglio");
        PB.inserisciEsercizio("Introduzione al Guinzaglio", "Insegnare ai proprietari come presentare il guinzaglio al cane in modo positivo, facendolo abituare gradualmente alla sensazione e premiando il comportamento calmo e collaborativo.");
        PB.confermaLezione();

        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);
        corsoAvanzato = PB.modificaProgrammaCorso().get(1);
        PB.selezionaCorso(corsoAvanzato);

        PB.nuovaLezione("Svolte, dietro front e variazioni andature");
        PB.inserisciEsercizio("Dietro Front", "Insegna al cane a girare rapidamente di 180 gradi su comando, promuovendo una risposta veloce e coordinata.");
        PB.confermaLezione();

        PB.nuovaLezione("Riporto in piano di un oggetto");
        PB.inserisciEsercizio("Introduzione al Riporto", "Insegna al cane a prendere un oggetto e a rilasciarlo gentilmente, utilizzando rinforzi positivi come premi o carezze.");
        PB.confermaLezione();

        // Inserimento turni 4 per la lezione 1 e 2 per la lezione 2
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(11, 0), LocalTime.of(12, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        

        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
      
        PB.inserisciNuovoCorso("Corso Agility", 10, 300.0F);
        corsoAgility = PB.getCorsi().get(2);
        PB.selezionaCorso(corsoAgility);

        PB.nuovaLezione("Abilità di Controllo e Fiducia");
        PB.inserisciEsercizio("Pausa", "Insegna al cane il comando \"Pausa\" per fermarsi istantaneamente, sviluppando il controllo e la risposta rapida alle istruzioni del proprietario.");
        PB.confermaLezione();

        // Iscriviamo Stella al corsoBase
        PB.accedi("Alberto1", "0000");
        Alberto = PB.getClienteLoggato();
        Stella = Alberto.getCane(1);
        PB.setCaneSelezionato(Stella);
        PB.confermaIscrizioneCorso(corsoBase);

        // Iscriviamo Maya al corsoBase
        Maya = Alberto.getCane(3);
        PB.setCaneSelezionato(Maya);
        PB.confermaIscrizioneCorso(corsoBase);

        // Iscriviamo Maya al corsoBase
        Leila = Alberto.getCane(4);
        PB.selezionaPeriodo(PB.getPeriodiAffido().getLast());
        PB.confermaAffido(Leila);

        
        // Mettiamo Stella in Affido nel periodo1
        PB.selezionaPeriodo(PB.getPeriodiAffido().getFirst());
        PB.confermaAffido(Stella);

        // Facciamo in modo che Duchessa abbia completato tutti i corsi
        Duchessa = Alberto.getCane(5);
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(0));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(1));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(2));


        

        
        

    }




    /* Test regole di dominio R2-R4 */
    @Test
    void confermaConclusioneAffido(){

    }

    /* Test regole di dominio R1-R3 */
    @Test
    void confermaTimbroTurno(){

    }










}
