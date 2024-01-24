package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ForkController {

    @FXML
    private Button button;

    @FXML
    void onAdminPressed(ActionEvent event) {
        // passiamo alla schermata di login per l'admin
        try {
            Stage finestraCorrente = (Stage) button.getScene().getWindow();
            finestraCorrente.close();
            MainApplication.setRootAndChangePlatform("admin_login-view.fxml", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onUserPressed(ActionEvent event) {
        // passiamo alla schermata dedicata al cliente
        try {
            Stage finestraCorrente = (Stage) button.getScene().getWindow();
            finestraCorrente.close();
            MainApplication.setRootAndChangePlatform("login_or_register-view.fxml", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
