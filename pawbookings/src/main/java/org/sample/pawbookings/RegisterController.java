package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private TextField surname;

    @FXML
    private TextField tel;

    @FXML
    private Button backButton;
    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        try {
            Stage finestraCorrente = (Stage) backButton.getScene().getWindow();
            finestraCorrente.close();
            MainApplication.goBackRoot(true);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onRegisterPressed(ActionEvent event) throws IOException {
        // recuperiamo le informazione inserite dall'utente
        String name = this.name.getText();
        String surname = this.surname.getText();
        String tel = this.tel.getText();
        String password = this.password.getText();

        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error-view.fxml"));
        Parent secondRoot = loader.load();

        // ne recuperiamo il relativo controller
        ErrorController errorController = loader.getController();

        // validiamole
        if (!name.isEmpty() && !surname.isEmpty() && !password.isEmpty() && !tel.isEmpty()) {
            // operazione di sistema
            PawBookings PB = PawBookings.getInstance();  
            try {
                PB.registrati(name, surname, tel, password);
                MainApplication.setRoot("ok-view.fxml");
            } catch (Exception e) {
                // utente non trovato... quindi:
                // messaggio errore
                errorController.setTextError(e.getMessage());
                // andiamo nella schermata di errore
                MainApplication.goTo(secondRoot);
            }
        } else {
            // i campi sono vuoti...

            // messaggio errore
            errorController.setTextError("devi prima compilare tutti i campi");
            // andiamo nella schermata di errore
            MainApplication.goTo(secondRoot);
        }
    }

}