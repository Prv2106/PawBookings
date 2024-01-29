package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ExerciseAddedController {

    @FXML
    void onContinuePressed(ActionEvent event) {
        try {
            MainApplication.setRoot("new_exercise-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onFinishPressed(ActionEvent event) {
        try {
            if (PawBookings.getInstance().confermaLezione()) {
                MainApplication.setRoot("admin_home-view.fxml");
            } else {
                MainApplication.simpleBack();
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  

}

