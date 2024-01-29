package org.sample.pawbookings;

import java.io.IOException;
import java.util.LinkedList;

import domain_layer.PawBookings;
import domain_layer.Turno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;


public class ChooseActivityController {
    private ErrorController errorController;

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

    private void goErrorPage(String text) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("error-view.fxml"));
            Parent root = loader.load();
            errorController = loader.getController();
            errorController.setTextError(text);
            MainApplication.goTo(root);
        } catch (Exception e) {

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
            goErrorPage("probabilmente il cane non è iscritto ad un corso");
        }
    }

    @FXML
    void onPrenotaTurnoLezioneClicked(ActionEvent event) throws IOException {
        // passiamo alla schermata che mostra l'elenco dei turni disponibili
        try {
            MainApplication.setRoot("available_shifts-view.fxml"); 
        } catch (IOException e) {
            // probabilmente il cane non è iscritto al turno.... quindi:
            goErrorPage("probabilmente il cane non è iscritto ad un corso");
        }
    }

    @FXML
    void onScambiaTurnoClicked(ActionEvent event) {
        // passiamo alla schermata che mostra l'elenco dei turni disponibili
        try {
            PawBookings PB = PawBookings.getInstance();
            LinkedList<Turno> elencoTurni = PB.scambioTurno();
            if (elencoTurni == null) {
                // nessun turno prenotato
                goErrorPage("Nessun turno ancora prenotato!");
            } else if (elencoTurni.isEmpty()) {
                // nessun turno disponibile
                goErrorPage("Nessun turno disponibile!");
            } else {
                // andiamo nella pagina coi turni disponibili
                MainApplication.setRoot("available_shifts_to_switch-view.fxml");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}