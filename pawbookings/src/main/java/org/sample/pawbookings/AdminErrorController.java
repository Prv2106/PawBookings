package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Controller for the administrator error screen.
 */
public class AdminErrorController {

    @FXML
    private TextFlow textError;

    /**
     * Displays an error message.
     *
     * @param txt message to display
     */
    public void setTextError(String txt) {
        this.textError.getChildren().add(new Text(txt));
    }

    @FXML
    void onPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
