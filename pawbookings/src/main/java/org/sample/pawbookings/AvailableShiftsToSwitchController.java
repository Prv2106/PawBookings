package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import domain_layer.PawBookings;
import domain_layer.Turno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Controller for the list of shifts available for an exchange. Replaces the current
 * booking with the selected shift.
 */
public class AvailableShiftsToSwitchController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Turno> list;

    ObservableList<Turno> items = FXCollections.observableArrayList();

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the exchange-shift list and configures the cell renderer and exchange handler.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();
        LinkedList<Turno> elencoTurni = PB.scambioTurno();

        items.addAll(elencoTurni);
        this.list.setItems(items);

        this.list.setCellFactory(new Callback<ListView<Turno>, ListCell<Turno>>() {
            @Override
            public ListCell<Turno> call(ListView<Turno> listView) {
                return new ListCell<Turno>() {
                    @Override
                    protected void updateItem(Turno turno, boolean empty) {

                        super.updateItem(turno, empty);

                        if (turno == null || empty) {
                            setText(null);
                        } else {

                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label("Data: " + turno.getData().getDayOfMonth() + "/" + turno.getData().getMonthValue() + "/" + turno.getData().getYear()));
                            vbox.getChildren().add(new Label("Ora Inizio: " + turno.getOraInizio()));
                            vbox.getChildren().add(new Label("Ora fine: " + turno.getOraFine()));

                            setGraphic(vbox);

                            setOnMouseClicked(event -> {

                                PB.selezionaTurnoScambio(turno);

                                    try {
                                        MainApplication.setRoot("ok-view.fxml");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                            });
                        }
                    }
                };
            }
        });

    }

}
