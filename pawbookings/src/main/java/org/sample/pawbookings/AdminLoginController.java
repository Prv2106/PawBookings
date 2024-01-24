package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        
        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_error-view.fxml"));
        Parent secondRoot = loader.load();

        // ne recuperiamo il relativo controller
        AdminErrorController errorController = loader.getController();

        // validiamole
        if (!pin.isEmpty()) {
            // operazione di sistema
            PawBookings PB = PawBookings.getInstance();  
            try {
                if (PB.accediComeAdmin(Integer.parseInt(pin))) {
                    MainApplication.setRoot("admin_home-view.fxml");
                } else {
                    // pin errato... quindi:
                    // messaggio errore
                    errorController.setTextError("PIN ERRATO");
                    // andiamo nella schermata di errore
                    MainApplication.goTo(secondRoot);
                }
            } catch (Exception e) {
                errorController.setTextError(e.getMessage());
                // andiamo nella schermata di errore
                MainApplication.goTo(secondRoot);
            }
        } else {
            // i campi sono vuoti...

            // messaggio errore
            errorController.setTextError("non puoi lasciare il campo vuoto");
            // andiamo nella schermata di errore
            MainApplication.goTo(secondRoot);
        }
    }
}
