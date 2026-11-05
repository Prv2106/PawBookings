/**
 * JavaFX application module for PawBookings.
 *
 * <p>Exports the presentation package to JavaFX FXML and opens it for
 * reflective controller injection.</p>
 */
module org.sample.pawbookings {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.sample.pawbookings to javafx.fxml;
    exports org.sample.pawbookings;
}
