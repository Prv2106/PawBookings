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
import javafx.util.Callback;

/**
 * Controller for selecting the dog to place in the chosen foster period.
 */
public class SelectDogController {

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

    /**
     * Populates the dog list for foster-care selection.
     *
     * @param elencoCaniDaAffidare dogs eligible for foster care
     */
    public void start(LinkedList<Cane> elencoCaniDaAffidare) {
        PawBookings PB = PawBookings.getInstance();

        if (elencoCaniDaAffidare.isEmpty()) {
            text.setText("Nessun cane disponibile per l'affido");
            text.setFont(new Font(16));

        } else {
            items.addAll(elencoCaniDaAffidare);
            this.list.setItems(items);

            this.list.setCellFactory(new Callback<ListView<Cane>, ListCell<Cane>>() {
                @Override
                public ListCell<Cane> call(ListView<Cane> listView) {
                    return new ListCell<Cane>() {
                        @Override
                        protected void updateItem(Cane cane, boolean empty) {

                            super.updateItem(cane, empty);

                            if (cane == null || empty) {
                                setText(null);
                            } else {

                                VBox vbox = new VBox();
                                Label nomeLabel = new Label("Nome: " + cane.getNome());
                                Label razzaLabel = new Label("Razza: " + cane.getRazza());
                                vbox.getChildren().addAll(nomeLabel, razzaLabel);

                                setGraphic(vbox);

                                setOnMouseClicked(event -> {
                                    try {

                                        boolean b = PB.confermaAffido(cane);
                                        if (b) {

                                            MainApplication.setRoot("ok-view.fxml");
                                        } else if (!b) {
                                            MainApplication.goClientErrorPage("Hai selezionato un periodo durante il quale il cane ha attiva la prenotazione ad un turno...");
                                        } else {

                                            MainApplication.goClientErrorPage("Il periodo di affido risulta adesso essere non più disponibile...");
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
