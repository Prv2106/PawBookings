package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class NewDogController {

    @FXML
    private TextField nome;

    @FXML
    private TextField razza;

    @FXML
    void onNewDogPressed(ActionEvent event) throws IOException{
        // recuperiamo le informazione inserite dall'utente
        String nome = this.nome.getText();
        String razza = this.razza.getText();

        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-view.fxml"));
        Parent secondRoot = loader.load();
        // ne recuperiamo il relativo controller
        ErrorController errorController = loader.getController();

        // validiamole
        if (!nome.isEmpty() && !razza.isEmpty()) {
            // operazione di sistema
            PawBookings PB = PawBookings.getInstance();  
            try {
                PB.aggiungiCane(nome, razza);
                MainApplication.setRoot("ok-view.fxml");
            } catch (Exception e) {
                // messaggio errore
                errorController.setTextError(e.getMessage());
                // destinazione schermata successiva: sempre il login
                errorController.setFXML("new_dog-view.fxml");
                // andiamo nella schermata
                MainApplication.goTo(secondRoot);
            }
        } else {
            // i campi sono vuoti...

            // messaggio errore
            errorController.setTextError("devi prima compilare entrambi i campi");
            // destinazione schermata successiva: sempre il login
            errorController.setFXML("new_dog-view.fxml");
            // andiamo nella schermata
            MainApplication.goTo(secondRoot);
        }
    }

}
