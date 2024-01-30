package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NewDogController {

    @FXML
    private TextField nome;

    @FXML
    private TextField razza;

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
    void onNewDogPressed(ActionEvent event) throws IOException{
        // recuperiamo le informazione inserite dall'utente
        String nome = this.nome.getText();
        String razza = this.razza.getText();

        // validiamole
        if (!nome.isEmpty() && !razza.isEmpty()) {
            // operazione di sistema
            PawBookings PB = PawBookings.getInstance();  
            try {
                PB.aggiungiCane(nome, razza);
                MainApplication.setRoot("ok-view.fxml");
            } catch (Exception e) {
                // messaggio errore
                MainApplication.goClientErrorPage(e.getMessage());
            }
        } else {
            // i campi sono vuoti...
           MainApplication.goClientErrorPage("devi prima compilare entrambi i campi!");
        }
    }

}
