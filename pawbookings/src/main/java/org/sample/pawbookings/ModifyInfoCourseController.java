package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModifyInfoCourseController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TextField capienza;

    @FXML
    private TextField costo;

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // allo start, settiamo nei TextFields i valori attuali
        this.capienza.setText(String.valueOf(PawBookings.getInstance().getCorsoSelezionato().getCapienza()));
        this.costo.setText(String.valueOf(PawBookings.getInstance().getCorsoSelezionato().getCosto()));   
    }

    @FXML
    void onInserisciNuovoCorsoPressed(ActionEvent event) throws IOException{
        // recuperiamo le informazione inserite dall'utente
        Integer capienza = Integer.parseInt(this.capienza.getText());
        float costo = Float.parseFloat(this.costo.getText());

        // validiamole
        if (capienza > 0 && costo > 0) {
            try {
                if (PawBookings.getInstance().modificaCorso(capienza, costo)) {
                    MainApplication.setRoot("admin_ok-view.fxml");
                } else {
                    MainApplication.goAdminErrorPage("Qualcosa è andato storto durante la modifica del corso...");
                }
            } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
            }
        } else {
            // i campi sono vuoti...
            MainApplication.goAdminErrorPage("Devi prima compilare tutti i campi!");
        }
    }

    

}
