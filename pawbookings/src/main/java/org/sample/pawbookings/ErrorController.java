package org.sample.pawbookings;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class ErrorController {
    @FXML
    private TextArea textError;

    public void setTextError(String txt) {
        this.textError.setText(txt);
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