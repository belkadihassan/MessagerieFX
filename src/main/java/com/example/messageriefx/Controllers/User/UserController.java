package com.example.messageriefx.Controllers.User;

import com.example.messageriefx.Controllers.Session;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public Button btn;
    public VBox addMailBox;
    public TextField inputMail;
    public PasswordField inputPsMail;
    public Button addMailBtn;
    public Button exitAddMail;
    public Button addEmail;
    private String username;


    public void setUsername(String username) {this.username = username;}
    public String getUsername(){return username;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addMailBox.setVisible(false);
        addEmail.setOnAction(e-> {
            addMailBox.setVisible(true);
            addEmail.setVisible(false);
        });
        exitAddMail.setOnAction(e-> {
            addMailBox.setVisible(false);
            addEmail.setVisible(true);
        });

        addMailBtn.setOnAction(e-> {
            Session.setEmail(inputMail.getText());

        });

        btn.setOnAction(e->{
            String user_name = Session.getCurrentUser();
            this.setUsername(user_name);
            System.out.println("username : " + this.username);
        });
    }
}
