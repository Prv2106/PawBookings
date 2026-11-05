package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller that lets the administrator choose between a direct pickup and a
 * delegated pickup.
 */
public class AdminChooseConcludiAffidoController {

    @FXML
    private Button backButton;

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onRitiroClientePressed(ActionEvent event) {

        try {
            MainApplication.setRoot("concludi_affido-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onRitiroConDelegaPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("concludi_affido_delega-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
