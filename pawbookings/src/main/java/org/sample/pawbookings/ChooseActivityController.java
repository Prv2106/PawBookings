package org.sample.pawbookings;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ChooseActivityController {

    @FXML
    private Button nuovaIscrizioneCorsoButton;

    @FXML
    private Button prenotaTurnoLezioneButton;


    @FXML
    void onNuovaIscrizioneCorsoClicked(MouseEvent event) {
        // passiamo alla schermata che mostra l'elenco dei corsi disponibili
        try {
            MainApplication.setRoot("available_courses-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}