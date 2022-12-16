module com.example.messageriefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.dlsc.formsfx;

    opens com.example.messageriefx to javafx.fxml;

    exports com.example.messageriefx;
    exports com.example.messageriefx.Controllers;
    exports com.example.messageriefx.Controllers.User;
}