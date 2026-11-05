package org.sample.pawbookings;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for a direct foster-care pickup performed by the customer.
 */
public class ConcludiAffidoController extends ConcludiAffido {

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

        String codiceCliente = this.codiceCliente.getText();
        String codiceCane = this.codiceCane.getText();
        goNext(codiceCliente, codiceCane, false);
    }

}
