package org.sample.pawbookings;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;
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
import javafx.util.Callback;

/**
 * Controller for displaying attended and missing lessons of the selected dog.
 */
public class ShowCourseStateController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Lezione> list1;

    ObservableList<Lezione> lezioniSeguite = FXCollections.observableArrayList();

    @FXML
    private ListView<Lezione> list2;

    ObservableList<Lezione> lezioniMancanti = FXCollections.observableArrayList();

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays attended and missing lessons for the selected dog.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();
        Map<String, LinkedList<Lezione>> lista = PB.mostraStatoAvanzamentoCorso();

        lezioniSeguite.addAll(lista.get("lezioni seguite"));
        this.list1.setItems(lezioniSeguite);

        lezioniMancanti.addAll(lista.get("lezioni mancanti"));
        this.list2.setItems(lezioniMancanti);

            this.list1.setCellFactory(new Callback<ListView<Lezione>, ListCell<Lezione>>() {
                @Override
                public ListCell<Lezione> call(ListView<Lezione> listView) {
                    return new ListCell<Lezione>() {
                        @Override
                        protected void updateItem(Lezione lezione, boolean empty) {

                            super.updateItem(lezione, empty);

                            if (lezione == null || empty) {
                                setText(null);
                            } else {

                                VBox vbox = new VBox();
                                vbox.getChildren().add(new Label(lezione.getNome()));
                                setGraphic(vbox);
                            }
                        }
                    };
                }
            });

            this.list2.setCellFactory(new Callback<ListView<Lezione>, ListCell<Lezione>>() {
                @Override
                public ListCell<Lezione> call(ListView<Lezione> listView) {
                    return new ListCell<Lezione>() {
                        @Override
                        protected void updateItem(Lezione lezione, boolean empty) {

                            super.updateItem(lezione, empty);

                            if (lezione == null || empty) {
                                setText(null);
                            } else {

                                VBox vbox = new VBox();
                                vbox.getChildren().add(new Label(lezione.getNome()));
                                setGraphic(vbox);
                            }
                        }
                    };
                }
            });

    }

}
