package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import domain_layer.Corso;
import domain_layer.Lezione;
import domain_layer.PawBookings;
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
import javafx.scene.text.Text;
import javafx.util.Callback;

public class AdminShowCourseProgramController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Lezione> list;
    // creiamo una lista osservabile per le lezioni
    ObservableList<Lezione> items = FXCollections.observableArrayList();

    @FXML
    private Text nomeCorsoSelezionato;

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PawBookings PB = PawBookings.getInstance();
        this.nomeCorsoSelezionato.setText(PB.getCorsoSelezionato());
        LinkedList<Lezione> programma = PB.getCorsoSelezionato().getLezioni();
        items.addAll(programma);
        this.list.setItems(items);

        // definiamo la grafica di ogni oggetto della lista (lezione)
        this.list.setCellFactory(new Callback<ListView<Lezione>, ListCell<Lezione>>() {
            @Override
            public ListCell<Lezione> call(ListView<Lezione> listView) {
                return new ListCell<Lezione>() {
                    int counter = 0;
                    @Override
                    protected void updateItem(Lezione lezione, boolean empty) {
                        // per ogni elemento (lezione)
                        super.updateItem(lezione, empty);

                        if (lezione == null || empty) {
                            setText(null);
                        } else {
                            // nome lezione
                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label("lezione " + counter + ": " + lezione.getNome()));
                            // sotto ogni lezione, mettiamone gli esercizi
                            for (int j = 0; j < lezione.getEsercizi().siize(); j++) {
                                Esercizio esercizio = lezione.getEsercizi().get(j);
                                vbox.getChildren().add(new Label("   - " + (j+1) +") " + esercizio.getNome()));
                            }
                            // Imposta il contenuto della cella
                            setGraphic(vbox);
                          }
                            
    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                // Azioni da eseguire quando un elemento viene cliccato
                                PB.selezionaLezione(lezione);

                                // passiamo alla schermata successiva
                                try {
                                    MainApplication.setRoot("new_shift-view");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    };
                }
            });
        }

    }
