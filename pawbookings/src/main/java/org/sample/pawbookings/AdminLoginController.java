package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for the administrator PIN authentication screen.
 */
public class AdminLoginController {

    @FXML
    private TextField pin;

    @FXML
    private Button backButton;
    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        try {
            MainApplication.simpleBack();;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLoginPressed(ActionEvent event) throws IOException{

        String pin = this.pin.getText();

        if (!pin.isEmpty()) {

            PawBookings PB = PawBookings.getInstance();
            try {
                if (PB.accediComeAdmin(Integer.parseInt(pin))) {
                    MainApplication.setRoot("admin_home-view.fxml");
                } else {

                    MainApplication.goAdminErrorPage("Pin errato!");
                }
            } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
            }
        } else {

            MainApplication.goAdminErrorPage("Non puoi lasciare il campo vuoto!");
        }
    }
}
