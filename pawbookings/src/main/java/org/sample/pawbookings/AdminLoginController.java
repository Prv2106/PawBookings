package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        // recuperiamo le informazione inserite dall'amministratore
        String pin = this.pin.getText();
        
        // validiamole
        if (!pin.isEmpty()) {
            // operazione di sistema
            PawBookings PB = PawBookings.getInstance();  
            try {
                if (PB.accediComeAdmin(Integer.parseInt(pin))) {
                    MainApplication.setRoot("admin_home-view.fxml");
                } else {
                    // pin errato... quindi:
                    MainApplication.goAdminErrorPage("Pin errato!");
                }
            } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
            }
        } else {
            // i campi sono vuoti...
            MainApplication.goAdminErrorPage("Non puoi lasciare il campo vuoto!");
        }
    }
}
