package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import domain_layer.Cane;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class RemoveDogController implements Initializable {

    @FXML
    private ListView<Cane> list;
    ObservableList<Cane> items = FXCollections.observableArrayList();

    @FXML
    private Text text;

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

    // metodo che viene richiamato all'apertura della schermata
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();  
        LinkedList<Cane> listaCani = PB.rimuoviCane();

        if (listaCani.isEmpty()) {
            text.setText("Nessun cane disponibile per la rimozione");
            text.setFont(new Font(16));
        } else {
            items.addAll(listaCani);
            this.list.setItems(items);

            // definiamo la grafica di ogni oggetto della lista
            this.list.setCellFactory(new Callback<ListView<Cane>, ListCell<Cane>>() {
                @Override
                public ListCell<Cane> call(ListView<Cane> listView) {
                    return new ListCell<Cane>() {
                        @Override
                        protected void updateItem(Cane cane, boolean empty) {
                            // per ogni elemento (cane)
                            super.updateItem(cane, empty);

                            if (cane == null || empty) {
                                setText(null);
                            } else {
                                // nome e razza
                                VBox vbox = new VBox();
                                Label nomeLabel = new Label("Nome: " + cane.getNome());
                                Label razzaLabel = new Label("Razza: " + cane.getRazza());
                                vbox.getChildren().addAll(nomeLabel, razzaLabel);

                                // Imposta il contenuto della cella
                                setGraphic(vbox);

        // -------------------> Aggiungiamo il listener per l'evento di click
                                setOnMouseClicked(event -> {
                                    try {
                                        // Azioni da eseguire quando un elemento viene cliccato
                                        if (PB.confermaRimozioneCane(cane)) {
                                            MainApplication.setRoot("ok-view.fxml");
                                        }
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

}
