package org.sample.pawbookings;

import java.io.IOException;
import domain_layer.Corso;
import domain_layer.Esercizio;
import domain_layer.Lezione;
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
import javafx.util.Callback;

/**
 * Controller for the administrator course list. Renders every course and routes to the
 * selected edit screen.
 */
public class AdminAllCoursesController {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Corso> list;

    ObservableList<Corso> items = FXCollections.observableArrayList();

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the course list and routes each selection to the requested screen.
     *
     * @param nextDestination FXML document opened after a course is selected
     * @throws IOException if the destination cannot be resolved
     */
    public void initialize(String nextDestination) throws IOException {
        items.addAll(PawBookings.getInstance().getCorsi());
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
                            vbox.getChildren().add(new Label("Tipo Corso: " + corso.getTipoCorso()));

                            if (nextDestination.equals("modify_info_course-view.fxml")) {

                                vbox.getChildren().add(new Label("Capienza: " + corso.getCapienza()));
                                vbox.getChildren().add(new Label("Costo: " + corso.getCosto() + "€"));
                            } else {

                                vbox.getChildren().add(new Label("Programma (num. lezioni: " + corso.getLezioni().size() +")"));
                                for (int i = 0; i < corso.getLezioni().size(); i++) {
                                Lezione lezione = corso.getLezioni().get(i);
                                vbox.getChildren().add(new Label("   - " + (i+1) +") " + lezione.getNome()));

                                    for (int j = 0; j < lezione.getEsercizi().size(); j++) {
                                        Esercizio esercizio = lezione.getEsercizi().get(j);
                                        vbox.getChildren().add(new Label("        - " + (j+1) +") " + esercizio.getNome()));
                                    }
                                }
                            }

                            setGraphic(vbox);

                            setOnMouseClicked(event -> {

                                PawBookings.getInstance().selezionaCorso(corso);

                                try {
                                    MainApplication.setRoot(nextDestination);
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

