package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginOrRegisterController  {

    @FXML
    private Button accedi;

    @FXML
    private Button backButton;
    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        try {
            Stage finestraCorrente = (Stage) backButton.getScene().getWindow();
            finestraCorrente.close();
            MainApplication.goBackRoot(true);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLoginPressed(ActionEvent event) {
         // passiamo alla schermata di login per l'utente
        try {
            MainApplication.setRoot("login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onRegisterPressed(ActionEvent event) {
        // passiamo alla schermata di registrazione per l'utente
        try {
            MainApplication.setRoot("register-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
