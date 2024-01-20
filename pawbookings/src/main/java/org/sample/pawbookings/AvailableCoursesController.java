package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.Callback;

import domain_layer.Cane;
import domain_layer.Corso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class AvailableCoursesController implements Initializable {
    @FXML
    ListView<Corso> list; // oggetto ListView recuperato dal file fxml (id=list)

    // creiamo una lista osservabile
    ObservableList<Corso> items = FXCollections.observableArrayList();


    // metodo che viene richiamato all'apertura della schermata
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // popoliamo la lista
        this.list.setItems(items);
        // List<Corso> corsiDisponibili = PawBookings.getInstance().getCorsi();
        // items.addAll(corsiDisponibili);
        
    }
}