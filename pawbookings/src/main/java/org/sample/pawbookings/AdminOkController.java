package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class AdminOkController {

    @FXML
    private Text textError;

    @FXML
    void onPressed(ActionEvent event) {
        try {
            MainApplication.setRoot("admin_home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
