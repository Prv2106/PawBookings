package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AdminHomeController {

    @FXML
    void onConcludiAffidoPressed(ActionEvent event) {
        // passiamo alla schermata dove inserire le info date dal cliente in struttura
        try {
            MainApplication.setRoot("concludi_affido-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLogoutPressed(ActionEvent event) {
        try {
            MainApplication.setRoot("fork-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
