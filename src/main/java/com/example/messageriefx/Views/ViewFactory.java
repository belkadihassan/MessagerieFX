package com.example.messageriefx.Views;

import com.example.messageriefx.App;
import com.example.messageriefx.Controllers.Session;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

public class ViewFactory {
    public ViewFactory(){}
    public void showLoginFrame() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(fxmlLoader , "Messagerie (Login)");
    }
    public void showUserUI(String username){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/User/User.fxml"));
        Session.startSession(username);
        createStage(fxmlLoader , "Messagerie (home)");
    }
    public void showSignINFrame(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/SignIN/SignIN.fxml"));
        createStage(fxmlLoader , "Messagerie (signIN)");
    }

    public void createStage(FXMLLoader loader , String title){
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        }catch(Exception e){
            System.out.println("scene not loaded");
            e.fillInStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/images/R.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    public void closeStage(Stage stage){
        System.out.println("window closed");
        stage.close();
    }
}
