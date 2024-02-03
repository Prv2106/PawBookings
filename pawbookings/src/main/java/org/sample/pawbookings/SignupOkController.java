package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SignupOkController {

    @FXML
    private Text codiceClienteLabel;

    @FXML
    void onBackToHomePressed(ActionEvent event) throws IOException {
        try {
            MainApplication.setRoot("home_page-view.fxml");
        } catch (Exception e) {
            MainApplication.goClientErrorPage("qualcosa è andato storto...");
        }
    }

    public void setCodiceCliente(String codiceCliente) {
        this.codiceClienteLabel.setText(codiceCliente);
    }
}
