package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import domain_layer.Esercizio;
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

/**
 * Controller for displaying the programme of a course selected by the administrator
 * and for selecting a lesson to manage its shifts.
 */
public class AdminShowCourseProgramController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Lezione> list;

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

    /**
     * Displays the selected course programme and configures lesson selection.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PawBookings PB = PawBookings.getInstance();
        this.nomeCorsoSelezionato.setText(PB.getCorsoSelezionato().getTipoCorso());

        LinkedList<Lezione> programma = PB.getCorsoSelezionato().getLezioni();
        items.addAll(programma);
        this.list.setItems(items);

        this.list.setCellFactory(new Callback<ListView<Lezione>, ListCell<Lezione>>() {
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
                            vbox.getChildren().add(new Label("lezione " + (programma.indexOf(lezione)+1) + ": " + lezione.getNome()));

                            for (int j = 0; j < lezione.getEsercizi().size(); j++) {
                                Esercizio esercizio = lezione.getEsercizi().get(j);
                                vbox.getChildren().add(new Label("   - " + (j+1) +") " + esercizio.getNome()));
                            }

                            setGraphic(vbox);

                          }

                            setOnMouseClicked(event -> {

                                PB.selezionaLezione(lezione);

                                try {
                                    MainApplication.setRoot("new_shift-view.fxml");
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
