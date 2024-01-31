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
        // recuperiamo quanto inserito dall'amministratore
        // recuperiamo le informazione inserite dall'amministratore
        String codiceCliente = this.codiceCliente.getText();
        String codiceCane = this.codiceCane.getText();

        if (!codiceCliente.isEmpty() && !codiceCane.isEmpty()) {
        // operazione di sistema
        PawBookings PB = PawBookings.getInstance();  
        
        try {
            Turno t = PB.timbraPrenotazioneTurno(codiceCliente, Integer.parseInt(codiceCane));

            if (t != null) {
                // recuperiamo il loader relativa alla schermata di dettagli turno prenotato (fxml)
                FXMLLoader ld = new FXMLLoader(getClass().getResource("dettagli_turno-view.fxml"));
                Parent root = ld.load(); // qui viene chiamato l'eventuale INITIALIZE
                // ne recuperiamo il relativo controller
                DettagliTurnoController controller = ld.getController();
                controller.start(t);
                // andiamo nella schermata
                MainApplication.goTo(root);
            } else {
                // nessun affido trovato sulla base dei dati inseriti:
                MainApplication.goAdminErrorPage("Nessuna corrispondenza trovata");
              }
            } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
            }
        } else {
        // i campi sono vuoti...
        MainApplication.goAdminErrorPage("Devi prima compilare tutti i campi!");
        }
    }    

}
