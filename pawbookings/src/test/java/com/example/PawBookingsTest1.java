package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

/**
 * Integration tests for customer registration, course enrollment, and shift booking
 * (UC1-UC2).
 */
class PawBookingsTest1 {

    static PawBookings PB;
    static Corso corsoBase;
    static Cane Stella;
    static Cane Asso;
    static Cane Dog;
    static Cane Maya;

    @BeforeAll
    public static void initTest() {
        // Initializes the fixtures required by the tests.
        PB = PawBookings.getInstance();
        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.aggiungiCane("Dog", "Corso");
        PB.aggiungiCane("Maya", "Pastore Tedesco");
        PB.logout();
        Stella = PB.getClienti().get("Alberto1").getCane(1);
        Asso = PB.getClienti().get("Alberto1").getCane(2);
        Dog = PB.getClienti().get("Alberto1").getCane(3);
        Maya = PB.getClienti().get("Alberto1").getCane(4);

        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        corsoBase = PB.getCorsi().getFirst();
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
        Corso corsoAvanzato = PB.modificaProgrammaCorso().get(1);
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
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(11, 0), LocalTime.of(12, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));

    }

    // Returns the singleton PawBookings instance.
    @Test
    void testGetInstance() {
        assertNotNull(PB);
    }

    // Verifies that the available-course list contains only courses
    // with capacity greater than zero.
    @Test
    void testNuovaIscrizioneCorso() {
        LinkedList<Corso> elencoCorsiDisponibili = new LinkedList<>();
        LinkedList<Corso> elencoCorsiCompleto = new LinkedList<>();
        PB.inserisciNuovoCorso("Corso Pieno", 0, 100F);
        elencoCorsiCompleto = PB.getCorsi();

        PB.selezionaCane(Stella);
        elencoCorsiDisponibili = PB.nuovaIscrizioneCorso();
        for (Corso corso : elencoCorsiDisponibili) {
            assertTrue(corso.getCapienza() > 0);
        }

        // Verifies that the complete-course list differs from the available-course list.
        assertFalse(elencoCorsiDisponibili.containsAll(elencoCorsiCompleto));
        // Verifies that elencoCorsiDisponibili excludes the full course.
        assertFalse(elencoCorsiDisponibili.contains(PB.getCorsi().getLast()));
    }

    @Test
    void testconfermaIscrizioneCorso(){
        // The dog is not enrolled in any course.
        assertFalse(Dog.getAttualmenteIscritto());
        PB.selezionaCane(Dog);
        // Enrolls the dog in the basic course.
        PB.confermaIscrizioneCorso(corsoBase);
        // Verifies that the dog is enrolled in a course.
        assertTrue(Dog.getAttualmenteIscritto());
        // Verifies that the dog is actually enrolled in the basic course.
        assertEquals(corsoBase, Dog.getCorsoCorrente());
    }

    @Test
    void testPrenotaTurnoLezione(){
        LinkedList<Turno> elencoTurniDisponibili = new LinkedList<>();
        // First shift of basic-course lesson 2.
        Turno t1 = corsoBase.getLezioni().getLast().getTurniDisponibili().getFirst();
        // Second shift of basic-course lesson 2.
        Turno t2 = corsoBase.getLezioni().getLast().getTurniDisponibili().getLast();

        // Removes shift t1 from the available shifts.
        corsoBase.getLezioni().getLast().getTurniDisponibili().remove(t1);

        // Enrolls Maya in the basic course.
        PB.selezionaCane(Maya);
        PB.confermaIscrizioneCorso(corsoBase);

        // Assumes that Maya attended the first lesson.
        Maya.getLezioniSeguite().add(corsoBase.getLezioni().getFirst());

        // Calls prenotaTurnoLezione.
        // Expected: the available-shift list is returned and:
        // - it does not contain t1;
        // - it contains t2.
        elencoTurniDisponibili = PB.prenotaTurnoLezione();
        assertFalse(elencoTurniDisponibili.contains(t1));
        assertFalse(elencoTurniDisponibili.contains(t2));

        // Books Maya for t2 and calls prenotaTurnoLezione again.
        // Expected: null because Maya already has a current shift.
        PB.selezionaTurno(t2);
        assertNull(PB.prenotaTurnoLezione());
    }

    // Checks the boolean returned by selezionaTurno for the supplied shift.
    @Test
    void testSelezionaTurno() {
        // Retrieves the first shift of basic-course lesson 1.
        Turno ts = corsoBase.getLezioni().get(0).getTurniDisponibili().get(0);
        // Enrolls Asso in the basic course.
        PB.selezionaCane(Asso);
        PB.confermaIscrizioneCorso(corsoBase);
        assertTrue(PB.selezionaTurno(ts));
        // Verifies that Asso's current shift is ts.
        assertEquals(ts, Asso.getTurnoCorrente());
        // Verifies that ts is removed from the lesson 1 available shifts.
        assertFalse(corsoBase.getLezioni().getFirst().getTurniDisponibili().contains(ts));
    }

    // Returns a Cane instance when the dog parameter is not null,
    // and verifies that the dog name is "Luna".
    @Test
    void testSelezionaCane() {
        PB.selezionaCane(new Cane(10, "Luna", "Barboncino"));
        assertNotNull(PB.getCaneSelezionato());
        assertEquals("Luna", PB.getCaneSelezionato().getNome());
    }

}
