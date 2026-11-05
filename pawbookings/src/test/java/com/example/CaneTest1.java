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

/**
 * Unit tests for dog enrollment, lesson progression, and course completion (UC1-UC2).
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
        // Initializes the fixtures required by the tests.
        PB = PawBookings.getInstance();
        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        corsoBase = PB.getCorsi().get(0);
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

        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);
        corsoAvanzato = PB.getCorsi().get(1);
        PB.selezionaCorso(corsoAvanzato);

        // The lesson code is 3.
        PB.nuovaLezione("Svolte, dietro front e variazioni andature");
        PB.inserisciEsercizio("Dietro Front", "Insegna al cane a girare rapidamente di 180 gradi su comando, promuovendo una risposta veloce e coordinata.");
        PB.inserisciEsercizio("Variazioni Andature", "Pratica diverse andature come il passo veloce e il passo lento, migliorando il controllo del cane nelle varie situazioni.");
        PB.inserisciEsercizio("Svolte", "Esercita il cane a eseguire svolte a destra e sinistra durante la camminata al guinzaglio, migliorando la coordinazione e la capacità di seguire le indicazioni del proprietario.");
        PB.confermaLezione();

        // The lesson code is 4.
        PB.nuovaLezione("Riporto in piano di un oggetto");
        PB.inserisciEsercizio("Introduzione al Riporto", "Insegna al cane a prendere un oggetto e a rilasciarlo gentilmente, utilizzando rinforzi positivi come premi o carezze.");
        PB.inserisciEsercizio("Riporto con Distrazioni", " Pratica il riporto introducendo distrazioni come suoni o altri oggetti, migliorando la capacità del cane di concentrarsi sul compito principale.");
        PB.inserisciEsercizio("Riporto a Distanza", "Insegna al cane a riportare un oggetto anche a distanza, incrementando gradualmente la distanza tra il proprietario e il punto in cui l'oggetto deve essere riportato.");
        PB.confermaLezione();

        // Adds shifts.

        PB.selezionaCorsoModificaTurni(corsoAvanzato);
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));

        Luna = new Cane(10, "Luna", "Barboncino");
        Stella = new Cane(11, "Stella", "Pastore Tedesco");
        Dog = new Cane(12, "Dog", "Pastore Tedesco");

        // Enrolls Stella in the basic course.
        Stella.aggiornaAttualmenteIscritto(corsoBase);

    }

    @Test
    void testAggiornaAttualmenteIscritto() {
        // The flag defaults to false.
        Cane cn = new Cane(10, "Sole", "Labrador");
        assertFalse(cn.getAttualmenteIscritto());
        assertEquals(null, cn.getCorsoCorrente());

        // Simulates that the dog enrolls in the basic course.
        cn.aggiornaAttualmenteIscritto(PB.getCorsi().get(0));
        assertTrue(cn.getAttualmenteIscritto());
        assertEquals(1, cn.getCorsoCorrente().getCodice());
    }

    @Test
    void testGetLezioneSuccessiva() {
        // Enrolls Luna in the basic course and verifies that the next lesson is the first.
        // Adds the first lesson to Luna's attended lessons so that
        // the next lesson is the second programme lesson.

        Luna.aggiornaAttualmenteIscritto(corsoBase);

        // Expected: the first lesson, because Luna has only enrolled in the basic course.
        assertEquals("Comandi di Base", Luna.getLezioneSuccessiva().getNome());

        // Assumes that Luna took the first lesson.
        Luna.aggiungiLezioneSeguita(PB.getCorsi().get(0).getLezioni().get(1));

        // Second check: the next lesson must be the second one.
        assertEquals("Guinzaglio e Camminare al Guinzaglio", Luna.getLezioneSuccessiva().getNome());

        // Restores the initial state.
        Luna.getLezioniSeguite().clear();
    }

    @Test
    void testAggiornaAvanzamentoCorso() {
        // 1) Verifies that the attended-lesson list size increases.
        // Verifies that the next lesson is added to the list.

        // Retrieves a dog.
        int lunghezzaPrecedente = Stella.getLezioniSeguite().size();

        // Retrieves the first shift of the first lesson of the enrolled basic course.
        Turno t1 = corsoBase.getLezioni().get(0).getTurniDisponibili().get(0);

        Stella.aggiornaStatoAvanzamento(t1);

        assertEquals(lunghezzaPrecedente + 1, Stella.getLezioniSeguite().size());

        // Verifies that the list of lessons attended by Stella contains the lesson.
        // associated with the chosen shift.
        assertTrue(Stella.getLezioniSeguite().contains(corsoBase.getLezioni().get(0)));

        // Restores the initial state.
        Stella.getLezioniSeguite().clear();

    }

    @Test
    void testCompletamentoCorso() {
        Turno t;
        // 2) Verifies the case in which the dog completes the course programme:
        // Enrolls the dog in the advanced course, assuming it completed the basic course.
        Dog.getCorsiCompletati().add(corsoBase);
        Dog.aggiornaAttualmenteIscritto(corsoAvanzato);

        // Simulates booking the dog for one shift of each advanced-course lesson.
        t = corsoAvanzato.getLezioni().get(0).getTurniDisponibili().get(0);
        Dog.aggiornaStatoAvanzamento(t);
        t = corsoAvanzato.getLezioni().get(1).getTurniDisponibili().get(0);
        Dog.aggiornaStatoAvanzamento(t);

         // Verifies that dog's attualmenteIscritto attribute has become false.
         assertFalse(Dog.getAttualmenteIscritto());

         // Verifies that dog's corsoCorrente attribute has become null.
         assertEquals(null, Dog.getCorsoCorrente());

         // Verifies that the length of dog's attended lessons list has become 2.
         assertEquals(2, Dog.getLezioniSeguite().size());

         // Verifies that the completed course is present in the dog's completed-course list.
         assertTrue(Dog.getCorsiCompletati().contains(corsoAvanzato));

         // Verifies that the Cane instance is no longer in the course enrolled-dog list.
         assertFalse(corsoAvanzato.getCaniIscritti().contains(Dog));

     }

}

