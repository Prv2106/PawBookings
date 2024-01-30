package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NewCourseController {

    @FXML
    private Button backButton;

    @FXML
    private TextField capienza;

    @FXML
    private TextField costo;

    @FXML
    private TextField tipoCorso;

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onInserisciNuovoCorsoPressed(ActionEvent event) throws IOException {
        // recuperiamo le informazione inserite dall'utente
        String tipoCorso = this.tipoCorso.getText();
        Integer capienza = Integer.parseInt(this.capienza.getText());
        Float costo = Float.parseFloat(this.costo.getText());

        // validiamole
        if (!tipoCorso.isEmpty() && capienza > 0 && costo > 0) {
            try {
                if (PawBookings.getInstance().inserisciNuovoCorso(tipoCorso, capienza, costo)) {
                    MainApplication.setRoot("admin_ok-view.fxml");
                } else {
                    // messaggio errore
                    MainApplication.goAdminErrorPage("Qualcosa è andato storto durante l'inserimento del corso...");
                }
            } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
            }
        } else {
            // i campi sono vuoti...
            MainApplication.goAdminErrorPage("devi prima compilare tutti i campi!");
        }
    }

}
