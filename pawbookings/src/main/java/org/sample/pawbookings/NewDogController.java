package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            Stage finestraCorrente = (Stage) backButton.getScene().getWindow();
            finestraCorrente.close();
            MainApplication.goBackRoot(true);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                // andiamo nella schermata di errore
                MainApplication.goTo(secondRoot);
            }
        } else {
            // i campi sono vuoti...

            // messaggio errore
            errorController.setTextError("devi prima compilare entrambi i campi");
            // andiamo nella schermata di errore
            MainApplication.goTo(secondRoot);
        }
    }

}
