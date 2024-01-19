module org.sample.pawbookings {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.sample.pawbookings to javafx.fxml;
    exports org.sample.pawbookings;
}