package org.sample.pawbookings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import domain_layer.Cane;
import domain_layer.PawBookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ShowDogsToRateController {
    private Map<Integer, TextField> mappaBoxsCommenti = new HashMap<>();

    @FXML
    private Button backButton;

    @FXML
    private ListView<Cane> list;
    // creiamo una lista osservabile per i cani
    ObservableList<Cane> items = FXCollections.observableArrayList();


    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(LinkedList<Cane> cani) {
        items.addAll(cani);
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
                                // a sx vogliamo le informazioni del cane, quindi: nome, razza e codice
                                // a dx vogliamo un TextField dove mettere il commento
                                HBox hBox = new HBox();
                                hBox.setAlignment(Pos.CENTER);

                                // elementi di sinistra
                                VBox vbox = new VBox();
                                vbox.setPrefWidth(150);
                                vbox.getChildren().add(new Label("Nome: " + cane.getNome()));
                                vbox.getChildren().add(new Label("Razza: " + cane.getRazza()));
                                vbox.getChildren().add(new Label("Codice: " + cane.getCodiceCane()));

                                // elemento di destra
                                TextField textField = new TextField();
                                textField.setPrefWidth(400);
                                mappaBoxsCommenti.put(cane.getCodiceCane(), textField);

                                // mettiamo gli elementi di sinistra e destra nell'hbox
                                hBox.getChildren().addAll(vbox, textField);

                                // Impostiiamo il contenuto della cella
                                setGraphic(hBox);
                            }
                        }
                    };
                }
            });
    }

    @FXML
    void onSendPressed(ActionEvent event) throws IOException {
        // travasiamoci le chiavi in una List (accessibile per posizione)
        List<Integer> codiciCani = new ArrayList<>();
        for (Integer k: mappaBoxsCommenti.keySet()) {
            codiciCani.add(k);
        }

        int counter = 0;
        Map<Integer, String> mappaStatoSalute = new HashMap<>();
        // cicliamo la mappa per ricavare i commenti di ogni cane
        for (TextField box: mappaBoxsCommenti.values()) {
            if (box.getText().isEmpty()) {
                // il box è vuoto
                MainApplication.goAdminErrorPage("devi compilare tutti i campi");
                return;
            } else {
                mappaStatoSalute.put(codiciCani.get(counter), box.getText());
            }
             counter++;
        }

        // operazione di sistema
        PawBookings.getInstance().notificaClienti(mappaStatoSalute);
        MainApplication.setRoot("admin_ok-view.fxml");
    }

}