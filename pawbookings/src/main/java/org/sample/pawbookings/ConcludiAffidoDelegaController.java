package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ConcludiAffidoDelegaController extends ConcludiAffido{

    @FXML
    private Button backButton;

    @FXML
    private TextField codiceCane;

    @FXML
    private TextField codiceDelega;

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onConcludiAffidoPressed(ActionEvent event) throws IOException {
        // recuperiamo le informazione inserite dall'amministratore
        String codiceDelega = this.codiceDelega.getText();
        String codiceCane = this.codiceCane.getText();
        goNext(codiceDelega, codiceCane, true);
    }

}
