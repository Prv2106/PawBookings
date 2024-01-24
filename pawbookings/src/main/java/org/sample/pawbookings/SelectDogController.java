package org.sample.pawbookings;

import java.io.IOException;
import java.util.LinkedList;

import domain_layer.Cane;
import domain_layer.PawBookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SelectDogController {

    @FXML
    private ListView<Cane> list;

    // creiamo una lista osservabile per la ListView<Cane>
    ObservableList<Cane> items = FXCollections.observableArrayList();

    @FXML
    private Text text;

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

    public void start(LinkedList<Cane> elencoCaniDaAffidare) {
        PawBookings PB = PawBookings.getInstance();
        if (elencoCaniDaAffidare.isEmpty()) {
            text.setText("Nessun cane disponibile per l'affido");
            text.setFont(new Font(16));
        } else {
            items.addAll(elencoCaniDaAffidare);
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
                                        if (PB.confermaAffido(cane)) {
                                            // notifica positiva
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
