package org.sample.pawbookings;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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

public class ReadNotificationsController implements Initializable {   

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> list;
    // creiamo una lista osservabile per le notifiche (stringhe)
    ObservableList<String> notifiche = FXCollections.observableArrayList();


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
        PawBookings PB = PawBookings.getInstance();  
        // avremo una mappa per ogni periodo affido al quale siamo "iscritti"
        LinkedList<Map<String, String>> listaMappa = PB.leggiStatoSalute();
        
        
        // unifichiamo le mappe in una sola
        Map<String, String> mappa = new HashMap<>();

        for (Map<String, String> m: listaMappa) {
            mappa.putAll(m);
        }

        // travasiamo le chiavi in una lista
        List<String> nomiCani = new ArrayList<>();
        for (String nomeCane: mappa.keySet()) {
            nomiCani.add(nomeCane);
        }


                
        notifiche.addAll(nomiCani);
        this.list.setItems(notifiche);

        
        // definiamo la grafica 
        this.list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String cane, boolean empty) {
                        // per ogni elemento (nome del cane)
                        super.updateItem(cane, empty);

                        if (cane == null || empty) {
                            setText(null);
                        } else {
                            // a sx il nome del cane, a dx il messaggio
                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label(cane + ":  " + mappa.get(cane)));
                            setGraphic(vbox);
                        }
                    }
                };
            }
        });
    }

}
