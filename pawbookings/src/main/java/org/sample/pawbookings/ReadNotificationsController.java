package org.sample.pawbookings;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private LinkedList<Map<String, String>> listaMappa;
    

    @FXML
    private Button backButton;

    @FXML
    private ListView<Map<String, String>> list;
    // creiamo una lista osservabile per le notifiche (stringhe)
    ObservableList<Map<String, String>> notifiche = FXCollections.observableArrayList();


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
        listaMappa = PB.leggiStatoSalute();
                
        notifiche.addAll(listaMappa);
        this.list.setItems(notifiche);

        

    
        // definiamo la grafica 
            
        this.list.setCellFactory(new Callback<ListView<Map<String, String>>, ListCell<Map<String, String>>>() {
            @Override
            public ListCell<Map<String, String>> call(ListView<Map<String, String>> listView) {
                return new ListCell<Map<String, String>>() {
                    
                    // travasiamo tutte le chiavi della mappa in una lista così da
                    // poter ottenere una determinata chiave
                    int counter = 0;
                    List<String> listaChiavi = new ArrayList<>();

                    @Override
                    protected void updateItem(Map<String, String> notifica, boolean empty) {
                        // per ogni elemento (notifica)
                        super.updateItem(notifica, empty);

                        for (String chiave: notifica.keySet()) 
                            listaChiavi.add(chiave);

                        if (notifica == null || empty) {
                            setText(null);
                        } else {
                            // a sx il nome del cane, a dx il messaggio
                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label(listaChiavi.get(counter) + ": " + notifica.get(listaChiavi.get(counter))));
                            setGraphic(vbox);
                        }
                         counter++;
                    }
                };
            }
        });
    }

}
