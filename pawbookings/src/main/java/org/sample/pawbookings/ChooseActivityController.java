package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;



public class ChooseActivityController {
    @FXML
    private Button backButton;
    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onNuovaIscrizioneCorsoClicked(ActionEvent event) {
        // passiamo alla schermata che mostra l'elenco dei corsi disponibili
        try {
            MainApplication.setRoot("available_courses-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onPrenotaTurnoLezioneClicked(ActionEvent event) throws IOException {
        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-view.fxml"));
        Parent secondRoot = loader.load();
        // ne recuperiamo il relativo controller
        ErrorController errorController = loader.getController();

        // passiamo alla schermata che mostra l'elenco dei turni disponibili
        try {
            if (PawBookings.getInstance().verificaIdoneitaPrenotazioneTurno()) {
                MainApplication.setRoot("available_shifts-view.fxml");
            } else {
                 // messaggio errore
                errorController.setTextError("Il cane non è iscritto ad un corso");

                // andiamo nella schermata di errore
                MainApplication.goTo(secondRoot);
            }
            
        } catch (IOException e) {         
            // messaggio errore
            errorController.setTextError(e.toString());

            // andiamo nella schermata di errore
            MainApplication.goTo(secondRoot);
        }
    }
}