package org.sample.pawbookings;
import java.io.IOException;
import domain_layer.Corso;
import domain_layer.Esercizio;
import domain_layer.Lezione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

/**
 * Controller for displaying a course programme.
 */
public class ShowProgramController {

    @FXML
    private Button backButton;

    @FXML
    private Text courseName;

    @FXML
    private ListView<Lezione> list;

    ObservableList<Lezione> items = FXCollections.observableArrayList();

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the programme of the supplied course.
     *
     * @param corso course whose programme is shown
     */
    public void initialize(Corso corso) {
        items.addAll(corso.getLezioni());
        this.list.setItems(items);

        this.courseName.setText(corso.getTipoCorso());

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
                            vbox.setMaxWidth(300);
                            vbox.getChildren().add(new Label("Nome lezione: " + lezione.getNome()));
                            vbox.getChildren().add(new Label("Esercizi (" + lezione.getEsercizi().size() + "):"));

                            for (int i = 0; i < lezione.getEsercizi().size(); i++) {
                                Esercizio esercizio = lezione.getEsercizi().get(i);
                                vbox.getChildren().add(new Label(" - " + (i+1) +") " + esercizio.getNome()));
                                TextFlow boxDescrizione = new TextFlow();

                                Text descrizione = new Text(esercizio.getDescrizione());
                                boxDescrizione.getChildren().add(descrizione);

                                vbox.getChildren().add(boxDescrizione);
                            }

                            setGraphic(vbox);

                        }
                    }
                };
            }
        });
    }

}
