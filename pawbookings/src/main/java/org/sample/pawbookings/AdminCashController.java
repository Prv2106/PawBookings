package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class AdminCashController {

    @FXML
    private Text importo;


    public void setAmount(float f) {
        this.importo.setText("" + f);
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
