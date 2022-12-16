package com.example.messageriefx.Controllers.User;

import com.example.messageriefx.Controllers.Session;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public Button btn;
    private String username;

    public void setUsername(String username) {this.username = username;}
    public String getUsername(){return username;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn.setOnAction(e->{
            String user_name = Session.getCurrentUser();
            this.setUsername(user_name);
            System.out.println("username : " + this.username);
        });
    }
}
