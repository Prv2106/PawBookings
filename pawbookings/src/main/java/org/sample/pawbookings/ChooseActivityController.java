package org.sample.pawbookings;

import java.io.IOException;
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

    // PULSANTI SCHERMATA

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
    void onMostraStatoAvanzamentoCorsoClicked(ActionEvent event) {
        // passiamo alla schermata che mostra lo stato di avanzamento del cane 'ShowCourseState'
        try {
            MainApplication.setRoot("show_course_state-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onPrenotaTurnoLezioneClicked(ActionEvent event) throws IOException {
        // passiamo alla schermata che mostra l'elenco dei turni disponibili
        try {
            MainApplication.setRoot("available_shifts-view.fxml"); 
        } catch (IOException e) {
                // probabilmente il cane non è iscritto al turno.... quindi:
    
                // recuperiamo il loader relativa alla schermata di errore (fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("error-view.fxml"));
                Parent secondRoot = loader.load();
                // ne recuperiamo il relativo controller
                ErrorController errorController = loader.getController();
    
                // messaggio errore
                errorController.setTextError("probabilmente il cane non è iscritto ad un corso");
                 // andiamo nella schermata di errore
            MainApplication.goTo(secondRoot);
        }
    }

    @FXML
    void onScambiaTurnoClicked(ActionEvent event) {
        // passiamo alla schermata che mostra l'elenco dei turni disponibili
        try {
            MainApplication.setRoot("available_shifts_to_switch-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}