package com.example.messageriefx;

import com.example.messageriefx.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showLoginFrame();
    }

}
