package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class ConcludiAffidoController {

    @FXML
    private TextField codiceCliente;

    @FXML
    private TextField codiceCane;

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
    void onConcludiAffidoPressed(ActionEvent event) throws IOException {
        // recuperiamo le informazione inserite dall'amministratore
        String codiceCliente = this.codiceCliente.getText();
        String codiceCane = this.codiceCane.getText();

        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_error-view.fxml"));
        Parent secondRoot = loader.load();

        // ne recuperiamo il relativo controller
        AdminErrorController errorController = loader.getController();

        // validiamole
        if (!codiceCliente.isEmpty() && !codiceCane.isEmpty()) {
            // operazione di sistema
            PawBookings PB = PawBookings.getInstance();  
            
            try {
                PeriodoAffido pa = PB.concludiAffido(codiceCliente, Integer.parseInt(codiceCane));
                if (pa != null) {
                    // recuperiamo il loader relativa alla schermata di dettagli affido (fxml)
                    FXMLLoader ld = new FXMLLoader(getClass().getResource("dettagli_affido-view.fxml"));
                    Parent root = ld.load(); // qui viene chiamato l'eventuale INITIALIZE
                    // ne recuperiamo il relativo controller
                    DettagliAffidoController controller = ld.getController();
                    controller.start(pa);
                    // andiamo nella schermata
                    MainApplication.goTo(root);
                } else {
                    // nessun affido trovato sulla base dei dati inseriti:
                    errorController.setTextError("Nessuna corrispondenza trovata");
                    // andiamo nella schermata di errore
                    MainApplication.goTo(secondRoot);
                }
            } catch (Exception e) {
                // nessun periodo trovato sulla base dei dati inseriti... quindi:
                    errorController.setTextError(e.getMessage());
                    // andiamo nella schermata di errore
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
