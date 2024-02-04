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

public class AvailableCoursesController implements Initializable {
    @FXML
    ListView<Corso> list; // oggetto ListView recuperato dal file fxml (id=list)

    // creiamo una lista osservabile per i corsi
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


    // metodo che viene richiamato all'apertura della schermata
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // popoliamo la lista
        PawBookings PB = PawBookings.getInstance();  
        LinkedList<Corso> corsiDisponibili = PB.nuovaIscrizioneCorso();
        
        items.addAll(corsiDisponibili);
        this.list.setItems(items);


        // definiamo la grafica di ogni oggetto della lista
        this.list.setCellFactory(new Callback<ListView<Corso>, ListCell<Corso>>() {
            @Override
            public ListCell<Corso> call(ListView<Corso> listView) {
                return new ListCell<Corso>() {
                    @Override
                    protected void updateItem(Corso corso, boolean empty) {
                        // per ogni elemento (corso)
                        super.updateItem(corso, empty);

                        if (corso == null || empty) {
                            setText(null);
                        } else {
                            // tipo corso e costo
                            VBox vbox = new VBox();
                            Label tipoCorsoLabel = new Label("Tipo Corso: " + corso.getTipoCorso());
                            Label costoLabel = new Label("Costo: " + corso.getCosto() + "€");
                            // programma
                            Label lezioniLabel = new Label("Programma (num. lezioni: " + corso.getLezioni().size() +")");
                            vbox.getChildren().addAll(tipoCorsoLabel, costoLabel, lezioniLabel);

                            for (int i = 0; i < corso.getLezioni().size(); i++) {
                                Lezione lezione = corso.getLezioni().get(i);
                                vbox.getChildren().add(new Label("   - " + (i+1) +") " + lezione.getNome()));
                            }

                            // Imposta il contenuto della cella
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                // Azioni da eseguire quando un elemento viene cliccato
                                

                                // passiamo alla schermata successiva
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