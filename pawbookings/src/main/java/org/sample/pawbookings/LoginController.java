package org.sample.pawbookings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;

import domain_layer.PawBookings;

public class LoginController {

    @FXML
    private TextField codice;

    @FXML
    private TextField password;

    @FXML
    void onLoginPressed(ActionEvent event) throws IOException {
        // recuperiamo le informazione inserite dall'utente
        String codice = this.codice.getText();
        String password = this.password.getText();

        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-view.fxml"));
        Parent secondRoot = loader.load();

        // ne recuperiamo il relativo controller
        ErrorController errorController = loader.getController();

        // validiamole
        if (!codice.isEmpty() && !password.isEmpty()) {
            // operazione di sistema
            PawBookings PB = PawBookings.getInstance();  
            try {
                PB.accedi(codice, password);
                MainApplication.setRoot("home_page-view.fxml");
            } catch (Exception e) {
                // utente non trovato... quindi:
                // messaggio errore
                errorController.setTextError("UTENTE NON TROVATO");
                // destinazione schermata successiva: sempre il login
                errorController.setFXML("login-view.fxml");
                // andiamo nella schermata
                MainApplication.goTo(secondRoot);
            }
        } else {
            // i campi sono vuoti...

            // messaggio errore
            errorController.setTextError("devi prima compilare entrambi i campi");
            // destinazione schermata successiva: sempre il login
            errorController.setFXML("login-view.fxml");
            // andiamo nella schermata
            MainApplication.goTo(secondRoot);
        }
    }

}
