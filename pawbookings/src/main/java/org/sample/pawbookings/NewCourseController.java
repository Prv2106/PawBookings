package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        String capienza = this.capienza.getText();
        String costo = this.costo.getText();

        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_error-view.fxml"));
        Parent secondRoot = loader.load();

        // ne recuperiamo il relativo controller
        AdminErrorController errorController = loader.getController();

        // validiamole
        if (!tipoCorso.isEmpty() && !capienza.isEmpty() && !costo.isEmpty()) {
            try {
                if (PawBookings.getInstance().inserisciNuovoCorso(tipoCorso, capienza, costo)) {
                    MainApplication.setRoot("admin_ok-view.fxml");
                } else {
                    // messaggio errore
                    errorController.setTextError("Qualcosa è andato storto durante l'inserimento del corso...");
                    // andiamo nella schermata di errore
                    MainApplication.goTo(secondRoot);
                }
            } catch (Exception e) {
                errorController.setTextError(e.getMessage());
                // andiamo nella schermatav di errore
                MainApplication.goTo(secondRoot);
            }
        } else {
            // i campi sono vuoti...

            // messaggio errore
            errorController.setTextError("devi prima compilare entrambi i campi");
            // andiamo nella schermatav di errore
            MainApplication.goTo(secondRoot);
        }
    }

}
