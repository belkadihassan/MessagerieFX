module com.example.messageriefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.dlsc.formsfx;
    requires javafx.web;
    requires java.mail;
    requires org.jsoup;

    opens com.example.messageriefx to javafx.fxml;

    exports com.example.messageriefx;
    exports com.example.messageriefx.Controllers;
    exports com.example.messageriefx.Controllers.User;
    exports com.example.messageriefx.Models;
    exports com.example.messageriefx.Views;
}