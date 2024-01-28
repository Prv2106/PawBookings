package org.sample.pawbookings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Stack;

import domain_layer.Corso;
import domain_layer.PawBookings; 
import domain_layer.PeriodoAffido;

public class MainApplication extends Application {
    private static Scene scene;
    private static Stack<Parent> stackRoots = new Stack<>();

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = new FXMLLoader(MainApplication.class.getResource("fork-view.fxml")).load();
        scene = new Scene(root);
        stackRoots.push(root);
        stage.setTitle("PawBookings-PC");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        Parent root = new FXMLLoader(MainApplication.class.getResource(fxml)).load();
        stackRoots.push(root);
        scene.setRoot(root);
    }

    static void goTo(Parent root) throws IOException {
        scene.setRoot(root);
        stackRoots.push(root);
    }

    static void goBackAndChangePlatform(boolean isMobile) throws IOException {
        stackRoots.pop();
        scene.setRoot(stackRoots.getLast());
        
        Stage stage = new Stage(); // nuova finestra
        if (isMobile) {
            stage.setTitle("PawBookings-mobile");
        }
        else {
            stage.setTitle("PawBookings-PC");
        }
        stage.setScene(scene);
        stage.show();
    }

    static void simpleBack() throws IOException {
        stackRoots.pop();
        scene.setRoot(stackRoots.getLast());
    }

    static void setRootAndChangePlatform(String fxml, boolean isMobile) throws IOException {
        Parent root = new FXMLLoader(MainApplication.class.getResource(fxml)).load();
        scene.setRoot(root);
        stackRoots.push(root);
        Stage stage = new Stage(); // nuova finestra
        if (isMobile) {
            stage.setTitle("PawBookings-mobile");
        }
        else {
            stage.setTitle("PawBookings-PC");
        }
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        PawBookings PB = PawBookings.getInstance();  
        
        // *** ISTRUZIONI PER SIMULARE DATI PERSISTENTI ***//

        // Il codice per accedere con alberto sarà Alberto1
        PB.registrati("Alberto", "Provenzano", "12345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.logout();

        // Il codice per accedere con Giuseppe sarà Giuseppe2
        PB.registrati("Giuseppe", "Leocata", "562562", "0000");
        PB.aggiungiCane("Walker", "Pastore Tedesco");
        PB.logout();

        // Il codice per accedere con Daniele sarà Daniele3
        PB.registrati("Daniele", "Lucifora", "9921319", "0000");
        PB.aggiungiCane("Sole", "Barboncino");
        PB.aggiungiCane("Luna", "Rottweiler");
        PB.aggiungiCane("Vanessa", "Labrador");
        

        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(0));
        PB.confermaAffido(PB.getClienti().get("Giuseppe2").getCane(3));


        // Il codice di Corso Base è 1
        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);

        // Il codice di Corso Avanzato è 2
        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);

        // Il codice di Corso Agility è 3
        PB.inserisciNuovoCorso("Corso Agility", 10, 300.0F);

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

        Corso corsoAvanzato = PB.modificaProgrammaCorso().get(1);
        PB.selezionaCorso(corsoAvanzato);

        // Il codice di questa lezione sarà 3
        PB.nuovaLezione("Svolte, dietro front e variazioni delle andature");
        PB.inserisciEsercizio("Dietro Front", "Insegna al cane a girare rapidamente di 180 gradi su comando, promuovendo una risposta veloce e coordinata.");
        PB.inserisciEsercizio("Variazioni delle Andature", "Pratica diverse andature come il passo veloce e il passo lento, migliorando il controllo del cane nelle varie situazioni.");
        PB.inserisciEsercizio("Svolte", "Esercita il cane a eseguire svolte a destra e sinistra durante la camminata al guinzaglio, migliorando la coordinazione e la capacità di seguire le indicazioni del proprietario.");
        PB.confermaLezione();

        // Il codice di questa lezione sarà 4
        PB.nuovaLezione("Riporto in piano di un oggetto");
        PB.inserisciEsercizio("Introduzione al Riporto", "Insegna al cane a prendere un oggetto e a rilasciarlo gentilmente, utilizzando rinforzi positivi come premi o carezze.");
        PB.inserisciEsercizio("Riporto con Distrazioni", " Pratica il riporto introducendo distrazioni come suoni o altri oggetti, migliorando la capacità del cane di concentrarsi sul compito principale.");
        PB.inserisciEsercizio("Riporto a Distanza", "Insegna al cane a riportare un oggetto anche a distanza, incrementando gradualmente la distanza tra il proprietario e il punto in cui l'oggetto deve essere riportato.");
        PB.confermaLezione();



        Corso corsoAgility = PB.modificaProgrammaCorso().get(2);
        PB.selezionaCorso(corsoAgility);

         // Il codice di questa lezione sarà 5
         PB.nuovaLezione("Abilità di Controllo e Fiducia");
         PB.inserisciEsercizio("Pausa", "Insegna al cane il comando \"Pausa\" per fermarsi istantaneamente, sviluppando il controllo e la risposta rapida alle istruzioni del proprietario.");
         PB.inserisciEsercizio("Affidamento su Comandi a Distanza", "Migliora la fiducia del cane nell'eseguire comandi anche a distanza, incoraggiando la comunicazione efficace tra il proprietario e il cane.");
         PB.inserisciEsercizio("Esplorazione Controllata", " Insegna al cane a esplorare nuovi ambienti mantenendo un controllo adeguato, rafforzando la fiducia nel proprietario e sviluppando un comportamento controllato durante l'esplorazione.");
         PB.confermaLezione();

         // Il codice di questa lezione sarà 6
         PB.nuovaLezione("Sequenze di Ostacoli");
         PB.inserisciEsercizio("Attraversamento a Zigzag", "Guida il cane attraverso una sequenza di ostacoli posti in uno schema a zigzag, sviluppando la coordinazione e la capacità di seguire istruzioni durante il percorso.");
         PB.inserisciEsercizio("Salto degli Ostacoli", "Insegna al cane a saltare ostacoli di diverse altezze in una sequenza, migliorando la forza e la precisione nei movimenti.");
         PB.inserisciEsercizio("Tunnel e Passaggi Angusti", "Pratica il passaggio attraverso tunnel e spazi angusti, incoraggiando il cane a seguire una sequenza specifica di ostacoli e a sviluppare agilità e destrezza.");
         PB.confermaLezione();


        // Iscrizione di un Cane di Daniele al Corso Base
        PB.accedi("Daniele3", "0000");
        PB.selezionaCane(PB.getClienteLoggato().getCani().get(0));
        PB.confermaIscrizioneCorso(corsoBase);

        // Iscrizione di un Cane di Daniele al Corso Avanzato
        PB.selezionaCane(PB.getClienteLoggato().getCani().get(1));
        PB.confermaIscrizioneCorso(corsoAvanzato);

        // Iscrizione di un Cane di Daniele al Corso Agility
        PB.selezionaCane(PB.getClienteLoggato().getCani().get(2));
        PB.confermaIscrizioneCorso(corsoAvanzato);
        
        PB.logout();

        // Inserimento turni 

        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        

        
        PB.selezionaCorsoModificaTurni(corsoAvanzato);
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));


        PB.selezionaCorsoModificaTurni(corsoAgility);
        PB.selezionaLezione(corsoAgility.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoAgility.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));


        
      
        launch(args);
    }
}