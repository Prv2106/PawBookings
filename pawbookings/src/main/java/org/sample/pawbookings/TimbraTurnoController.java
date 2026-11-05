package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for shift check-in. Resolves the booking from customer and dog codes.
 */
public class TimbraTurnoController {

    @FXML
    private Button backButton;

    @FXML
    private TextField codiceCliente;

    @FXML
    private TextField codiceCane;

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onTimbraPressed(ActionEvent event) throws IOException {

        String codiceCliente = this.codiceCliente.getText();
        String codiceCane = this.codiceCane.getText();

        if (!codiceCliente.isEmpty() && !codiceCane.isEmpty()) {

        PawBookings PB = PawBookings.getInstance();

        try {
            Turno t = PB.timbraPrenotazioneTurno(codiceCliente, Integer.parseInt(codiceCane));

            if (t != null) {

                FXMLLoader ld = new FXMLLoader(getClass().getResource("dettagli_turno-view.fxml"));
                Parent root = ld.load();

                DettagliTurnoController controller = ld.getController();
                controller.start(t);

                MainApplication.goTo(root);
            } else {

                MainApplication.goAdminErrorPage("Nessuna corrispondenza trovata");
              }
            } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
            }
        } else {

        MainApplication.goAdminErrorPage("Devi prima compilare tutti i campi!");
        }
    }

}
