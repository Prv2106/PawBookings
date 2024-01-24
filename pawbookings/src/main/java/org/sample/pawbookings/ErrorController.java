package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ErrorController {
    @FXML
    private Text textError;
    private String fxml;

    public void setTextError(String txt) {
        this.textError.setText(txt);
    }

    public void setFXML(String fxml) {
        this.fxml = fxml;
    }
  

    @FXML
    void onPressed(ActionEvent event) {
        try {
            MainApplication.setRoot(this.fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}