package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;


import org.junit.jupiter.api.BeforeAll;



/*
 * Test dei casi d'uso UC1-UC2
 */

class CaneTest1 {
    static PawBookings PB;
    static Cane Luna;
    static Cane Stella;
    static Cane Dog;
    static Corso corsoBase;
    static Corso corsoAvanzato;

    @BeforeAll
    public static void initTest() {
        //Configurazioni che ci servono prima dell'esecuzione dei metodi di test
        PB = PawBookings.getInstance();
        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        corsoBase = PB.getCorsi().get(0);
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

        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);
        corsoAvanzato = PB.getCorsi().get(1);
        PB.selezionaCorso(corsoAvanzato);

        // Il codice di questa lezione sarà 3
        PB.nuovaLezione("Svolte, dietro front e variazioni andature");
        PB.inserisciEsercizio("Dietro Front", "Insegna al cane a girare rapidamente di 180 gradi su comando, promuovendo una risposta veloce e coordinata.");
        PB.inserisciEsercizio("Variazioni Andature", "Pratica diverse andature come il passo veloce e il passo lento, migliorando il controllo del cane nelle varie situazioni.");
        PB.inserisciEsercizio("Svolte", "Esercita il cane a eseguire svolte a destra e sinistra durante la camminata al guinzaglio, migliorando la coordinazione e la capacità di seguire le indicazioni del proprietario.");
        PB.confermaLezione();

        // Il codice di questa lezione sarà 4
        PB.nuovaLezione("Riporto in piano di un oggetto");
        PB.inserisciEsercizio("Introduzione al Riporto", "Insegna al cane a prendere un oggetto e a rilasciarlo gentilmente, utilizzando rinforzi positivi come premi o carezze.");
        PB.inserisciEsercizio("Riporto con Distrazioni", " Pratica il riporto introducendo distrazioni come suoni o altri oggetti, migliorando la capacità del cane di concentrarsi sul compito principale.");
        PB.inserisciEsercizio("Riporto a Distanza", "Insegna al cane a riportare un oggetto anche a distanza, incrementando gradualmente la distanza tra il proprietario e il punto in cui l'oggetto deve essere riportato.");
        PB.confermaLezione();

        // Inserimento turni

        PB.selezionaCorsoModificaTurni(corsoAvanzato);
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));

        Luna = new Cane(10, "Luna", "Barboncino");
        Stella = new Cane(11, "Stella", "Pastore Tedesco");
        Dog = new Cane(12, "Dog", "Pastore Tedesco");

        // Iscriviamo Stella al corsoBase
        Stella.aggiornaAttualmenteIscritto(corsoBase);

        // PB.selezionaCane(new Cane(10, "Luna", "Barboncino"));
        // PB.getCaneSelezionato().aggiornaAttualmenteIscritto(PB.getCorsi().get(0));
    }
    

    @Test
    void testAggiornaAttualmenteIscritto() {
        // il booleano è settato a false di default
        Cane cn = new Cane(10, "Sole", "Labrador"); 
        assertFalse(cn.getAttualmenteIscritto());
        assertEquals(null, cn.getCorsoCorrente());

        // simuliamo che il cane si iscrivi al corso base
        cn.aggiornaAttualmenteIscritto(PB.getCorsi().get(0));
        assertTrue(cn.getAttualmenteIscritto());
        assertEquals(1, cn.getCorsoCorrente().getCodice());
    }



    @Test
    void testGetLezioneSuccessiva() { 
        // Iscriviamo Luna al corsoBase e verifichiamo che la lezione successiva sia la prima
        // dopo, aggiungiamo la prima lezione all'elenco delle lezioni seguite da Luna, 
        // così da verificare successivamente che la lezione successiva sia la seconda del programma

        /*  Il programma del corso base è il seguente:
            Lezione lezione1 = new Lezione(1,"Comandi di Base");
            Lezione lezione2 = new Lezione(2,"Guinzaglio e Camminare al Guinzaglio");
        */

        Luna.aggiornaAttualmenteIscritto(corsoBase);

        // ci aspettiamo che la lezione sia la prima in quanto Luna si è solo iscritta al corsoBase
        assertEquals("Comandi di Base", Luna.getLezioneSuccessiva().getNome());

        // assumiamo che Luna abbia seguito la prima lezione
        Luna.aggiungiLezioneSeguita(PB.getCorsi().get(0).getLezioni().get(1));

        // Seconda verifica: ci aspettiamo che la lezione successiva sia la seconda
        assertEquals("Guinzaglio e Camminare al Guinzaglio", Luna.getLezioneSuccessiva().getNome());

        // Ripristino lo stato iniziale
        Luna.getLezioniSeguite().clear();
    }


    
    @Test
    void testAggiornaAvanzamentoCorso() {
        // 1) dimensione elenco lezioni seguite, ovvero
        // controlliamo che la lezione successiva sia aggiunta all'elenco

        // prendiamo un cane
        int lunghezzaPrecedente = Stella.getLezioniSeguite().size();

       

        // recuperiamo il primo turno della prima lezione del corso base a cui è iscritto
        Turno t1 = corsoBase.getLezioni().get(0).getTurniDisponibili().get(0);

        Stella.aggiornaStatoAvanzamento(t1);

        assertEquals(lunghezzaPrecedente + 1, Stella.getLezioniSeguite().size());

        // verifichiamo che l'elenco delle lezioni seguite da Stella contenga la lezione
        // relativa al turno scelto
        assertTrue(Stella.getLezioniSeguite().contains(corsoBase.getLezioni().get(0)));

        // Ripristino lo stato iniziale
        Stella.getLezioniSeguite().clear();

    }
    



    @Test 
    void testCompletamentoCorso() {
        Turno t;
        // 2) verfichiamo il caso in cui il Dog termina il programma di un Corso:
        // Iscriviamo Dog al corsoAvanzato (per farlo assumiamo che abbia completato il corsoBase)
        Dog.getCorsiCompletati().add(corsoBase);
        Dog.aggiornaAttualmenteIscritto(corsoAvanzato);

        // Simuliamo la prenotazione di Dog ad un turno di ciascuna lezione del corsoAvanzato
        t = corsoAvanzato.getLezioni().get(0).getTurniDisponibili().get(0);
        Dog.aggiornaStatoAvanzamento(t);
        t = corsoAvanzato.getLezioni().get(1).getTurniDisponibili().get(0);
        Dog.aggiornaStatoAvanzamento(t);
       
 
         // Verifichiamo che l'attributo attualmenteIscritto di Dog è diventato false
         assertFalse(Dog.getAttualmenteIscritto());
 
         // Verifichiamo che l'attributo corsoCorrente di Dog è diventato null
         assertEquals(null, Dog.getCorsoCorrente());
 
         // Verifichiamo che la lunghezza della lista lezioniSeguite di Dog è diventata 2
         assertEquals(2, Dog.getLezioniSeguite().size());
 
         // Verifichiamo che nell'elenco Corsi completati di Dog sia presente il corso che abbiamo completato
         assertTrue(Dog.getCorsiCompletati().contains(corsoAvanzato));
 
         // Verifichiamo che l'istanza Dog di Cane non sia più presente nell'elencoCaniIscritti del Corso interessato
         assertFalse(corsoAvanzato.getCaniIscritti().contains(Dog));
 
     }




   





}












