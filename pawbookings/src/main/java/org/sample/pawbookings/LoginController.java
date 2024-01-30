package org.sample.pawbookings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.io.IOException;

import domain_layer.PawBookings;

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
        // recuperiamo le informazione inserite dall'utente
        String codice = this.codice.getText();
        String password = this.password.getText();

        // validiamole
        if (!codice.isEmpty() && !password.isEmpty()) {
            // operazione di sistema
            PawBookings PB = PawBookings.getInstance();  
            try {
                if (PB.accedi(codice, password)) {
                    MainApplication.setRoot("home_page-view.fxml");
                } else {
                    // utente non trovato
                    MainApplication.goClientErrorPage("Utente non trovato");
                }
            } catch (Exception e) {
                MainApplication.goClientErrorPage(e.getMessage());
            }
        } else {
            // i campi sono vuoti...
            MainApplication.goClientErrorPage("Devi prima compilare tutti i campi di testo!");
        }
    }

}
