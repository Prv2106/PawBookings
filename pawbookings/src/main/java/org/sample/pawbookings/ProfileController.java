package org.sample.pawbookings;

import java.net.URL;
import java.util.ResourceBundle;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class ProfileController implements Initializable {

    @FXML
    private Text username;

    @FXML
    void onNewDogPressed(ActionEvent event) {

    }

    @FXML
    void onRemoveDogPressed(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.username.setText(PawBookings.getInstance().getClienteLoggato().getNome());
    }

}
