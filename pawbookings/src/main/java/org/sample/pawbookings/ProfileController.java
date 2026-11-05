package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller for the customer profile. Exposes delegation, dog management, logout, and
 * navigation actions.
 */
public class ProfileController implements Initializable {

    @FXML
    private Text username;

    @FXML
    private Text label;

    @FXML
    private Text text;

    @FXML
    void onDelegaPressed(ActionEvent event) {
        label.setVisible(true);
        text.setVisible(true);
        text.setText("" + PawBookings.getInstance().delega());
    }

    @FXML
    void onNewDogPressed(ActionEvent event) {
        try {
            MainApplication.setRoot("new_dog-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onRemoveDogPressed(ActionEvent event) {
        try {
            MainApplication.setRoot("remove_dog-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLogoutPressed(ActionEvent event) {
        try {
            PawBookings.getInstance().logout();
            Stage finestraCorrente = (Stage) username.getScene().getWindow();
            finestraCorrente.close();
            MainApplication.setRootAndChangePlatform("fork-view.fxml", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDogTabPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("available_periods-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onHomeTabPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("home_page-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the logged-in customer code and delegation code when available.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.username.setText(PawBookings.getInstance().getClienteLoggato().getCodiceCliente());

        int codiceDelega = PawBookings.getInstance().getClienteLoggato().getCodiceDelega();
        if (codiceDelega != 0) {
            label.setVisible(true);
            text.setVisible(true);
            text.setText("" + codiceDelega);
        }
    }

}
