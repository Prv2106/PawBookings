package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.util.Callback;

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

/**
 * Controller for the list of courses available to the selected dog. Enrolls the dog in
 * the selected course.
 */
public class AvailableCoursesController implements Initializable {
    @FXML
    ListView<Corso> list;

    ObservableList<Corso> items = FXCollections.observableArrayList();

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
     * Populates the available-course list and configures the cell renderer and enrollment handler.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        PawBookings PB = PawBookings.getInstance();
        LinkedList<Corso> corsiDisponibili = PB.nuovaIscrizioneCorso();

        items.addAll(corsiDisponibili);
        this.list.setItems(items);

        this.list.setCellFactory(new Callback<ListView<Corso>, ListCell<Corso>>() {
            @Override
            public ListCell<Corso> call(ListView<Corso> listView) {
                return new ListCell<Corso>() {
                    @Override
                    protected void updateItem(Corso corso, boolean empty) {

                        super.updateItem(corso, empty);

                        if (corso == null || empty) {
                            setText(null);
                        } else {

                            VBox vbox = new VBox();
                            Label tipoCorsoLabel = new Label("Tipo Corso: " + corso.getTipoCorso());
                            Label costoLabel = new Label("Costo: " + corso.getCosto() + "€");

                            Label lezioniLabel = new Label("Programma (num. lezioni: " + corso.getLezioni().size() +")");
                            vbox.getChildren().addAll(tipoCorsoLabel, costoLabel, lezioniLabel);

                            for (int i = 0; i < corso.getLezioni().size(); i++) {
                                Lezione lezione = corso.getLezioni().get(i);
                                vbox.getChildren().add(new Label("   - " + (i+1) +") " + lezione.getNome()));
                            }

                            setGraphic(vbox);

                            setOnMouseClicked(event -> {

                                try {
                                    if (PB.confermaIscrizioneCorso(corso)) {
                                        MainApplication.setRoot("ok-view.fxml");
                                    } else {
                                        MainApplication.goClientErrorPage("il tuo cane non ha ancora completato il corso del livello inferiore, oppure hai selezionato un corso già conseguito dal tuo cane!");
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
