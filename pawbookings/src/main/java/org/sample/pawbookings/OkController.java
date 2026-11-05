package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for the customer confirmation screen.
 */
public class OkController {

    @FXML
    void onBackToHomePressed(ActionEvent event) {
        try {
            MainApplication.setRoot("home_page-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
