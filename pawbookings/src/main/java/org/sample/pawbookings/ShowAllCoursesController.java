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

/**
 * Controller for the list of all courses. Routes to the programme of the selected
 * course.
 */
public class ShowAllCoursesController implements Initializable{

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
     * Populates the course list and configures programme navigation.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();
        LinkedList<Corso> elencoCorsi = PB.getCorsi();

        items.addAll(elencoCorsi);
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
                            vbox.getChildren().add(new Label("Costo: " + corso.getCosto() + "€"));
                            setGraphic(vbox);

                            setOnMouseClicked(event -> {

                                try {

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("show_program-view.fxml"));

                                    Parent secondRoot = loader.load();

                                    ShowProgramController controller = loader.getController();

                                    controller.initialize(corso);

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
