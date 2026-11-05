package org.sample.pawbookings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import domain_layer.PawBookings;

/**
 * Controller for customer authentication.
 */
public class LoginController {

    @FXML
    private TextField codice;

    @FXML
    private TextField password;

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
    void onLoginPressed(ActionEvent event) throws IOException {

        String codice = this.codice.getText();
        String password = this.password.getText();

        if (!codice.isEmpty() && !password.isEmpty()) {

            PawBookings PB = PawBookings.getInstance();
            try {
                if (PB.accedi(codice, password)) {
                    MainApplication.setRoot("home_page-view.fxml");
                } else {

                    MainApplication.goClientErrorPage("Utente non trovato");
                }
            } catch (Exception e) {
                MainApplication.goClientErrorPage(e.getMessage());
            }
        } else {

            MainApplication.goClientErrorPage("Devi prima compilare tutti i campi di testo!");
        }
    }

}
