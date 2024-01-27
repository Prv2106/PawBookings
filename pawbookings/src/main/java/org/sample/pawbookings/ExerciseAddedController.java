package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ExerciseAddedController {

    @FXML
    void onContinuePressed(ActionEvent event) {
        try {
            MainApplication.setRoot("new_exercise-view");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onFinishPressed(ActionEvent event) {
        try {
            MainApplication.setRoot("admin_home-view");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  

}

