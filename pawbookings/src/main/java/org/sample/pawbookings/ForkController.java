package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller for the initial platform selection screen. Routes to the administrator or
 * customer area.
 */
public class ForkController {

    @FXML
    private Button button;

    @FXML
    void onAdminPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("admin_login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onUserPressed(ActionEvent event) {

        try {
            Stage finestraCorrente = (Stage) button.getScene().getWindow();
            finestraCorrente.close();
            MainApplication.setRootAndChangePlatform("login_or_register-view.fxml", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
