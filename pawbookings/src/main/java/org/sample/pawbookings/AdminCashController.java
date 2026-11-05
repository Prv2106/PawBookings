package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller for the administrator payment summary screen. Displays the amount to
 * collect.
 */
public class AdminCashController {

    @FXML
    private Text importo;

    /**
     * Displays the amount to collect.
     *
     * @param amount amount to display
     */
    public void setAmount(float amount) {
        this.importo.setText("" + String.format("%.2f", amount) + "€");
    }

    @FXML
    void onBackToHomePressed(ActionEvent event) throws IOException {
        try {
            MainApplication.setRoot("admin_home-view.fxml");
        } catch (Exception e) {
            MainApplication.goAdminErrorPage("qualcosa è andato storto...");
        }
    }

}
