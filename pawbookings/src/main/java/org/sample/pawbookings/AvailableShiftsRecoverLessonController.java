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

public class AvailableShiftsRecoverLessonController implements Initializable {

    @FXML
    private ListView<Turno> list;

    // creiamo una lista osservabile per i turni
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();  
        LinkedList<Turno> elencoTurni = PB.recuperaLezione();

        items.addAll(elencoTurni);
        this.list.setItems(items);

        this.list.setCellFactory(new Callback<ListView<Turno>, ListCell<Turno>>() {
            @Override
            public ListCell<Turno> call(ListView<Turno> listView) {
                return new ListCell<Turno>() {
                    @Override
                    protected void updateItem(Turno turno, boolean empty) {
                        // per ogni elemento (turno)
                        super.updateItem(turno, empty);

                        if (turno == null || empty) {
                            setText(null);
                        } else {
                            // data, ora inizio e ora fine
                            VBox vbox = new VBox();
                            Label dataLabel = new Label("Data: " + turno.getData());
                            Label oraInizioLabel = new Label("Ora Inizio: " + turno.getOraInizio());
                            Label oraFineLabel = new Label("Ora fine: " + turno.getOraFine());

                            vbox.getChildren().addAll(dataLabel, oraInizioLabel, oraFineLabel);

                            // Imposta il contenuto della cella
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                // Azioni da eseguire quando un elemento viene cliccato
                                try {
                                    boolean b = PB.selezionaTurnoRecupero(turno);
                                    if (b) {
                                        MainApplication.setRoot("ok-view.fxml");
                                    } else  {
                                        MainApplication.goClientErrorPage("Il tuo cane risulta essere in affido per quel periodo");
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
