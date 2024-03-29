package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
            MainApplication.simpleBack();
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

        // validiamole
        if (!name.isEmpty() && !surname.isEmpty() && !password.isEmpty() && !tel.isEmpty()) {
            // operazione di sistema
            PawBookings PB = PawBookings.getInstance();  
            try {
                String codiceCliente = PB.registrati(name, surname, tel, password);
                if (codiceCliente != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("signup_ok-view.fxml"));
                    Parent root = loader.load();
                    SignupOkController controller = loader.getController();
                    controller.setCodiceCliente(codiceCliente);
                    MainApplication.goTo(root);
                } else {
                    // utente già iscritto
                    MainApplication.goClientErrorPage("Il numero di telefono inserito risulta già presente nel sistema");
                }
            } catch (Exception e) {
                MainApplication.goClientErrorPage(e.getMessage());
            }
        } else {
            // messaggio errore
            MainApplication.goClientErrorPage("devi prima compilare tutti i campi");
        }
    }

}