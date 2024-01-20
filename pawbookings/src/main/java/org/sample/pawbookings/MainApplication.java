package org.sample.pawbookings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("home_page-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("PawBookings");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(new FXMLLoader(MainApplication.class.getResource(fxml)).load());
    }

    public static void main(String[] args) {
        launch(args);
    }
}