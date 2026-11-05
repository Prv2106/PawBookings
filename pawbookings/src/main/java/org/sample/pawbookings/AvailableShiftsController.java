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
 * Controller for the list of shifts available to the selected dog. Books the selected
 * shift.
 */
public class AvailableShiftsController implements Initializable {

    @FXML
    private ListView<Turno> list;

    ObservableList<Turno> items = FXCollections.observableArrayList();

    @FXML
    private Button backButton;

    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the available-shift list and configures the cell renderer and booking handler.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        PawBookings PB = PawBookings.getInstance();
        LinkedList<Turno> elencoTurni = PB.prenotaTurnoLezione();

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
                            Label dataLabel = new Label("Data: " + turno.getData().getDayOfMonth() + "/" + turno.getData().getMonthValue() + "/" + turno.getData().getYear());
                            Label oraInizioLabel = new Label("Ora Inizio: " + turno.getOraInizio());
                            Label oraFineLabel = new Label("Ora fine: " + turno.getOraFine());

                            vbox.getChildren().addAll(dataLabel, oraInizioLabel, oraFineLabel);

                            setGraphic(vbox);

                            setOnMouseClicked(event -> {

                                try {
                                    boolean b = PB.selezionaTurno(turno);
                                    if (b) {
                                        MainApplication.setRoot("ok-view.fxml");
                                    } else if (b == false) {
                                        MainApplication.goClientErrorPage("Il tuo cane risulta essere in affido per quel periodo");
                                    } else {
                                        MainApplication.goClientErrorPage("Il turno non è più disponibile :(");
                                    }
                                } catch (Exception e) {
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
