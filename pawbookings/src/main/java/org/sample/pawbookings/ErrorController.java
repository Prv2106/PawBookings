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

    public void setTextError(String txt) {
        this.textError.setText(txt);
    }
  

    @FXML
    void onBackToHomePressed(ActionEvent event) {
        try {
            MainApplication.setRoot("home_page-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}