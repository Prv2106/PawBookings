package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for registering a dog for the logged-in customer.
 */
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

        String nome = this.nome.getText();
        String razza = this.razza.getText();

        if (!nome.isEmpty() && !razza.isEmpty()) {

            PawBookings PB = PawBookings.getInstance();
            try {
                PB.aggiungiCane(nome, razza);
                MainApplication.setRoot("ok-view.fxml");
            } catch (Exception e) {

                MainApplication.goClientErrorPage(e.getMessage());
            }
        } else {

           MainApplication.goClientErrorPage("devi prima compilare entrambi i campi!");
        }
    }

}
