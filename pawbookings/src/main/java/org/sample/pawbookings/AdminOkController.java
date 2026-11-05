package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for the administrator confirmation screen.
 */
public class AdminOkController {

    @FXML
    void onPressed(ActionEvent event) {
        try {
            MainApplication.setRoot("admin_home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
