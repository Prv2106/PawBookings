package org.sample.pawbookings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;

public class MainApplication extends Application {
    private static Scene scene;
    private static Parent previousRoot;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fork-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("PawBookings-PC");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        previousRoot = scene.getRoot();
        Parent root = new FXMLLoader(MainApplication.class.getResource(fxml)).load();
        scene.setRoot(root);
    }

    static void goTo(Parent root) throws IOException {
        previousRoot = scene.getRoot();
        scene.setRoot(root);
    }

    static void goBackRoot(boolean isMobile) throws IOException {
        scene.setRoot(previousRoot);
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

    static void setRootAndChangePlatform(String fxml, boolean isMobile) throws IOException {
        Parent root = new FXMLLoader(MainApplication.class.getResource(fxml)).load();
        previousRoot = scene.getRoot();  
        scene.setRoot(root);
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
        PB.confermaAffido(PB.getClienti().get("Giuseppe2").getCane(1));
      
        launch(args);
    }
}