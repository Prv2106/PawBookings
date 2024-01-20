package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ChooseActivityController {

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
    void onPrenotaTurnoLezioneClicked(ActionEvent event) {
        // passiamo alla schermata che mostra l'elenco dei turni disponibili
        try {
            MainApplication.setRoot("available_shifts-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}