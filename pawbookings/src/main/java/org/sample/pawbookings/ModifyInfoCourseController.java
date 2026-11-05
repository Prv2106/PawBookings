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

/**
 * Controller for editing a course capacity and price.
 */
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

    /**
     * Prefills the form with the selected course capacity and price.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.capienza.setText(String.valueOf(PawBookings.getInstance().getCorsoSelezionato().getCapienza()));
        this.costo.setText(String.valueOf(PawBookings.getInstance().getCorsoSelezionato().getCosto()));
    }

    @FXML
    void onInserisciNuovoCorsoPressed(ActionEvent event) throws IOException{

        Integer capienza = Integer.parseInt(this.capienza.getText());
        float costo = Float.parseFloat(this.costo.getText());

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

            MainApplication.goAdminErrorPage("Devi prima compilare tutti i campi!");
        }
    }

}
