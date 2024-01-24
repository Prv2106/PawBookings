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
import javafx.stage.Stage;
import javafx.util.Callback;

public class AvailableShiftsController implements Initializable {

    @FXML
    private ListView<Turno> list;

    // creiamo una lista osservabile per i corsi
    ObservableList<Turno> items = FXCollections.observableArrayList();

    @FXML
    private Button backButton;
    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        try {
            Stage finestraCorrente = (Stage) backButton.getScene().getWindow();
            finestraCorrente.close();
            MainApplication.goBackRoot(true);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metodo che viene richiamato all'apertura della schermata
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // popoliamo la lista dinamica
        PawBookings PB = PawBookings.getInstance();  
        LinkedList<Turno> elencoTurni = PB.prenotaTurnoLezione();

        items.addAll(elencoTurni);
        this.list.setItems(items);

        // definiamo la grafica di ogni oggetto della lista
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
                            Label oraInizioLabel = new Label("Ora Inizio: " + turno.getOraFine());
                            Label oraFineLabel = new Label("Ora fine: " + turno.getOraFine());

                            vbox.getChildren().addAll(dataLabel, oraInizioLabel, oraFineLabel);

                            // Imposta il contenuto della cella
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                // Azioni da eseguire quando un elemento viene cliccato
                                try {
                                    PB.selezionaTurno(turno);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
    
                                // passiamo alla schermata successiva
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
