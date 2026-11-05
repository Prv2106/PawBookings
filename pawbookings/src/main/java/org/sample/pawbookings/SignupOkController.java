package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller shown after successful registration. Displays the generated customer
 * code.
 */
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

    /**
     * Displays the generated customer code.
     *
     * @param codiceCliente customer code
     */
    public void setCodiceCliente(String codiceCliente) {
        this.codiceClienteLabel.setText(codiceCliente);
    }
}
