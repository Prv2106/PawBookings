package org.sample.pawbookings;

import domain_layer.Turno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class AvailableShiftsToSwitchController {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Turno> list;

    @FXML
    void onBackPressed(ActionEvent event) {

    }

}