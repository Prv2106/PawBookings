package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import domain_layer.Corso;
import domain_layer.PawBookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ShowAllCoursesController implements Initializable{

    @FXML
    private Button backButton;

    @FXML
    private ListView<Corso> list;

    // creiamo una lista osservabile per i corsi
    ObservableList<Corso> items = FXCollections.observableArrayList();

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // popoliamo la lista
        PawBookings PB = PawBookings.getInstance();  
        LinkedList<Corso> elencoCorsi = PB.getElencoCorsi();
        
        items.addAll(elencoCorsi);
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
                            vbox.getChildren().add(new Label("Tipo Corso: " + corso.getTipoCorso()));
                            vbox.getChildren().add(new Label("Costo: " + corso.getCosto() + "€"));
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                // passiamo alla schermata successiva passando il corso cliccato
                                try {
                                     // recuperiamo il loader relativa alla schermata di visualizzazione del programma
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("show_program-view.fxml"));
                                    // reperiamone la "root"
                                    Parent secondRoot = loader.load();
                                    // ne recuperiamo il relativo controller
                                    ShowProgramController controller = loader.getController();
                        
                                    // passiamogli il parametro corso e facciamolo "partire"
                                    controller.initialize(corso);

                                    // impostiamo la root 
                                    MainApplication.goTo(secondRoot);
                                    
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
