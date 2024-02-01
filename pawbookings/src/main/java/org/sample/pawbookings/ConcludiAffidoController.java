package org.sample.pawbookings;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


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
        // recuperiamo le informazione inserite dall'amministratore
        String codiceCliente = this.codiceCliente.getText();
        String codiceCane = this.codiceCane.getText();
        goNext(codiceCliente, codiceCane, false);
    }

}
