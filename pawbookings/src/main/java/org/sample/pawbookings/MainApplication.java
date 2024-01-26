package org.sample.pawbookings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

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
        
        // Istruzioni per simulare dei dati persistenti

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




        /*  DATI CORSI
         *  CorsoBase corsoBase = new CorsoBase(1,10,200.0F,"Corso Base");
            CorsoAvanzato corsoAvanzato = new CorsoAvanzato(2,10,250.0F,"Corso Avanzato");
            CorsoAgility corsoAgility = new CorsoAgility(3,5,300.0F,"Corso Agility");

         */



        /*
         * ROGRAMMI CORSI
         */

        /* CORSO BASE
         *  Lezione lezione1 = new Lezione(1,"Comandi di Base");
            Lezione lezione2 = new Lezione(2,"Guinzaglio e Camminare al Guinzaglio");
            Lezione lezione3 = new Lezione(3,"Dai la Zampa e Seduto");
            Lezione lezione4 = new Lezione(4,"Controllo delle Impulsività");
        */


        /* CORSO AVANZATO
         *  Lezione l1 = new Lezione(1, "Messa al piede del cane‌");
            Lezione l2 = new Lezione(2, "Condotta senza guinzaglio‌");
            Lezione l3 = new Lezione(3, "Svolte, dietro front e variazioni delle andature");
            Lezione l4 = new Lezione(4, "Posizioni di seduto e terra in condotta”");
            Lezione l5 = new Lezione(5, "Riporto in piano di un oggetto");

        */


        /* CORSO AGILITY
         * Lezione l1 = new Lezione(1, "Introduzione all'Agility");
           Lezione l2 = new Lezione(2, "Comandi di Base‌");
           Lezione l3 = new Lezione(3, "Abilità di Controllo e Fiducia");
           Lezione l4 = new Lezione(4, "Sequenze di Ostacoli");
           Lezione l5 = new Lezione(5, "Gare Simulate"); 
        */



        /*  TURNI
         *  Turno t1 = new Turno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
            Turno t2 = new Turno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
            Turno t3 = new Turno(LocalDate.now().plusDays(1), LocalTime.of(18, 0), LocalTime.of(19, 0));
         */





      
        launch(args);
    }
}