package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;


/*
 * Test degli scenari alternativi dei casi d'uso degli UC1-UC2-UC3-UC4
 * Test del flusso base del caso d'uso UC14
 */


public class PawBookingsTest5 {
    static PawBookings PB;
    static Cane Stella;
    static Cane Duchessa;
    static Corso corsoBase;
    static Corso corsoAvanzato;
    static Corso corsoAgility;
    static Cane Walker;
    static Cane Maya;
    static PeriodoAffido periodo2;
    static Cane Leila;


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
        
        PB.registrati("Giuseppe", "Leocata", "562562", "0000");
        PB.aggiungiCane("Walker", "Pastore Tedesco");
        PB.logout();

        PB.registrati("Daniele", "Lucifora", "9921319", "0000");
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

        // Inserimento turni
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
        Stella = PB.getClienteLoggato().getCane(1);
        PB.setCaneSelezionato(Stella);
        PB.confermaIscrizioneCorso(corsoBase);

        // Iscriviamo Maya al corsoBase
        Maya = PB.getClienteLoggato().getCane(3);
        PB.setCaneSelezionato(Maya);
        PB.confermaIscrizioneCorso(corsoBase);

        // Iscriviamo Maya al corsoBase
        Leila = PB.getClienteLoggato().getCane(4);
        PB.selezionaPeriodo(PB.getPeriodiAffido().getLast());
        PB.confermaAffido(Leila);

        
        // Mettiamo Stella in Affido nel periodo1
        PB.selezionaPeriodo(PB.getPeriodiAffido().getFirst());
        PB.confermaAffido(Stella);

        // Facciamo in modo che Duchessa abbia completato tutti i corsi
        Duchessa = PB.getClienteLoggato().getCane(5);
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(0));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(1));
        Duchessa.getCorsiCompletati().add(PB.getCorsi().get(2));


        // Iscriviamo Walker al corsoBase
        PB.accedi("Giuseppe2", "0000");
        Walker = PB.getClienteLoggato().getCane(6);
        PB.setCaneSelezionato(Walker);
        PB.confermaIscrizioneCorso(corsoBase);

        // Facciamo in modo che il periodo 2 sia pieno
        periodo2 = PB.getPeriodiAffido().get(1);
        periodo2.setNumPosti(0);

       

    }



    @Test
    void testNuovaIscrizioneCorso(){
        /** Test UC1  estensione 3a **/
        // Stella è già iscritta al corsoBase, quindi provando ad effettuare la chiamata a nuovaIscrizioneCorso con Stella
        // Ci aspettiamo che venga restituito null
        PB.selezionaCane(Stella);
        assertEquals(null, PB.nuovaIscrizioneCorso());

        /** Test UC1  estensione 3b **/
        // Duchessa ha già completato tutti i corsi disponibili, quindi provando ad effettuare la chiamata a nuovaIscrizioneCorso con Duchessa
        // Ci aspettiamo che venga restituito null
        PB.selezionaCane(Duchessa);
        assertEquals(null, PB.nuovaIscrizioneCorso());
    }

    @Test
    void testConfermaIscrizioneCorso(){
        PB.accedi("Alberto1", "0000");
        // Asso non è iscritto ad alcun corso e non ha completato ancora alcun corso
        Cane Asso = PB.getClienteLoggato().getCane(2);
        PB.selezionaCane(Asso);

        /** Test UC1  estensione 5a **/
        // Iscrivendolo al corsoAvanzato 
        // Ci aspettiamo che venga restituito false in quanto non ha completato il corsoBase
        // Ci aspettiamo che non risulterà essere iscritto ad alcun corso
        assertFalse(PB.confermaIscrizioneCorso(corsoAvanzato));
        assertFalse(Asso.getAttualmenteIscritto());
        


    }

    @Test
    void testPrenotaTurnoLezione(){
        PB.accedi("Alberto1", "0000");
        /** Test UC2 estensione 3a **/
        // Asso non è iscritto ad alcun corso e non ha completato ancora alcun corso
        Cane Asso = PB.getClienteLoggato().getCane(2);
        PB.selezionaCane(Asso);

        // test del metodo
        // effettuando la chiamata al metodo prenotaTurnoLezione con Asso ci aspettiamo che venga restituito null
        assertEquals(null, PB.prenotaTurnoLezione());

        PB.selezionaCane(Stella);
        // Mentre effettuando la stessa chiamata con Stella (che è iscritta al corsoBase) avremo un valore diverso da null
        assertNotEquals(null, PB.prenotaTurnoLezione());

        /** Test UC2 estensione 3b **/
        // Settando il turno corrente di Stella ad un valore diverso da null ci aspettiamo che venga restituito false
        Turno t = new Turno(12, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12,0));
        Stella.setTurnoCorrente(t);
        assertEquals(null, PB.prenotaTurnoLezione());

    }


    @Test
    void testSelezionaTurno(){
        Turno turnoLezioneCorsoBase = corsoBase.getLezioni().getFirst().getTurniDisponibili().getLast();
        /** Test UC2 estensione 5b **/
        // Stella è in affido nel periodo 1 la cui durata è sovrapposta ai turni della lezione 1 del corsoBase
        // ci aspettiamo che venga restituito false
        PB.selezionaCane(Stella);
        assertFalse(PB.selezionaTurno(turnoLezioneCorsoBase));
    }



    @Test
    void testConfermaAffido(){
        /** Test UC3 estensione 6a **/
        // Cercando di affidare Duchessa nel periodo 2 (che non è più disponibile)
        // Ci aspettiamo che venga restituito false
        PB.accedi("Alberto1", "0000");
        PB.selezionaPeriodo(periodo2);
        assertFalse(PB.confermaAffido(Duchessa));

        /** Test UC3 estensione 6b **/
        // Cercando di affidare Walker nel periodo 1 dopo che l'abbiamo prenotato al primo turno della prima lezione del corso base
        // Ci aspettiamo che venga restituito false

        PB.accedi("Giuseppe2", "0000");
        PB.selezionaCane(Walker);
        Turno ts = corsoBase.getLezioni().getFirst().getTurniDisponibili().getFirst();
        PB.selezionaTurno(ts);
        assertEquals(ts, Walker.getTurnoCorrente());
        PB.selezionaPeriodo(PB.getPeriodiAffido().getFirst());
        assertFalse(PB.confermaAffido(Walker));

    }


    @Test
    void testDelega(){
        /** Test UC4 estensione 1b **/
        // Dopo che viene chiamato il metodo delega il codiceDelega del clienteLoggato è diverso da 0
        PB.accedi("Giuseppe2", "0000");
        assertEquals(0, PB.getClienteLoggato().getCodiceDelega());
        PB.delega();
        assertNotEquals(0, PB.getClienteLoggato().getCodiceDelega());
    }

    @Test
    void testConcludiAffidoDelega(){
        PeriodoAffido periodo3 =PB.getPeriodiAffido().getLast();
        /** Test UC4 estensione 4c **/
        // Alberto ha Leila in affido nel periodo 3
        assertEquals(Leila.getAffidoCorrente(), periodo3);  
        assertTrue(periodo3.getCaniAffido().contains(Leila));
        // Accedendo con Alberto e invocando il metodo delega, il codiceDelega di Alberto verrà settato
        PB.accedi("Alberto1", "0000");
        Cliente Alberto = PB.getClienteLoggato();
        PB.delega();
        PB.logout();
        // Invocando il metodo concludiAffidoDelega passando il codiceDelega e il codice di Leila 
        // Ci aspettiamo che stella venga rimossa dal periodo 1
        PB.concludiAffidoDelega(Alberto.getCodiceDelega(), 4);
        PB.confermaConclusioneAffido();
        assertNotEquals(Leila.getAffidoCorrente(), periodo3);  
        assertFalse(periodo3.getCaniAffido().contains(Leila));

    }


    @Test
    void testMostraStatoAvanzamentoCorso(){
        /** Test UC10 estensione 2a **/
        // Leila non è iscritta ad alcun corso
        // effettuando l'operazione mostraStatoAvanzamentoCorso
        // Ci aspettiamo che venga restituito false
        PB.selezionaCane(Leila);
        assertEquals(null,PB.mostraStatoAvanzamentoCorso());
    }


    @Test
    void testAccediComeAdmin(){
        /** Test UC4 estensione 3a **/
        // Inserendo Pin corretto -> 1234
        // Ci asepttiamo che il metodo accediComeAdmin restituisca true
        assertTrue(PB.accediComeAdmin(1234));

        // Inserendo un pin errato
        // Ci asepttiamo che il metodo accediComeAdmin restituisca false
        assertFalse(PB.accediComeAdmin(0232));
    }


    @Test
    void testConcludiAffido(){
        /** Test UC4 estensione 4a **/
        // Stella è in affido nel periodo 1
        // Inserendo un codiceCliente errato (e codiceCane giusto) nel metodo ConcludiAffido ci aspettiamo che venga restituito null
        assertEquals(null, PB.concludiAffido("126", 1)); 
        // Inserendo un codiceCane errato (e codiceCliente giusto) nel metodo ConcludiAffido ci aspettiamo che venga restituito null
        assertEquals(null, PB.concludiAffido("Alberto1", 3));
        // Inserendo i parametri errati nel metodo ConcludiAffido ci aspettiamo che venga restituito null
        assertEquals(null, PB.concludiAffido("Alberto123232", 3));
        /** Test UC4 estensione 4a **/
        // Asso non è in Affido 
        // Inserendo il codiceCliente e il codiceCane corretti nel metodo ConcludiAffido ci aspettiamo che venga restituito null
        assertEquals(null, PB.concludiAffido("Alberto1", 2));
    }

    @Test
    void testTimbraPrenotazioneTurno() {
        /** TEST UC14 flusso base **/

        // questo metodo, dato un codiceCliente ed un codiceCane,
        // restituisce l'attributo 'turnoCorrente' della classe Cane, 
        // il quale può essere null o un riferimento ad un'istanza di Turno.
        // Inoltre, anche se il codice del cliente o il codice del cane sono sbagliati
        // restituisce null.
        // -> quindi, testeremo le 3 eventuali casistiche per cui può tornare null ed anche
        //    la casistica in cui dovrebbe restituire un'istanza di Turno

        // invocando il metodo adesso, passando entrambi i parametri errati ci aspettiamo null
        assertNull(PB.timbraPrenotazioneTurno("sjhKD", 199));

        Cliente cl = new Cliente("Cliente1", "Lupo", "Alberto", "0000", "0123456789");
        Cane cn = new Cane(1, "Dog", "barboncino");
        cl.addCane(cn);
        PB.addCliente(cl);

        // se passiamo adesso il codice cliente corretto, ma il codice cane errato, ci aspettiamo null
        assertNull(PB.timbraPrenotazioneTurno("Cliente1", 199));
        
        // se passiamo adesso il codice cliente errato, ma il codice cane corretto, ci aspettiamo null
        assertNull(PB.timbraPrenotazioneTurno("Cliente1987", 1));

        // invocando il metodo adesso passando 'Cliente1' e '1', ci aspettiamo che restituisca null in quanto
        // il cane non ha effettuato nessuna prenotazione
        assertNull(PB.timbraPrenotazioneTurno("Cliente1", 1));

        // simuliamo una prenotazione 
        PB.setCaneSelezionato(cn);
        PB.confermaIscrizioneCorso(corsoBase);
        Turno turnoDisponibile = PB.prenotaTurnoLezione().getLast();
        PB.selezionaTurno(turnoDisponibile);

        // invocando il metodo adesso passando 'Cliente1' e '1', ci aspettiamo che restituisca il turno con codice..
        assertEquals(turnoDisponibile.getCodice() , PB.timbraPrenotazioneTurno("Cliente1", 1).getCodice());
    }

    @Test
    void testConfermaTimbroTurno() {
        /** TEST UC14 flusso base **/

        // questo metodo si occupa di settare a null il riferimento che possiede un Cane 
        // sull'istanza turno corrente della classe Turno
        
        // invochiamo il test precedente così da avere una prenotazione di un turno
        // associata al cane selezionato
        testTimbraPrenotazioneTurno();

        // testiamo che il turno associato al cane non sia null
        assertNotNull(PB.getCaneSelezionato().getTurnoCorrente());

        // inochiamo il metodo sotto test
        PB.confermaTimbroTurno();

         // testiamo che il turno associato al cane sia null
         assertNull(PB.getCaneSelezionato().getTurnoCorrente());
    }


}
