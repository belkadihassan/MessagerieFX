package com.example.messageriefx.Controllers;


import com.example.messageriefx.Models.Model;

import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label username_lbl;
    public TextField username_fld;
    public TextField email_fld;
    public TextField room_fld;
    public PasswordField mailPassword_fld;
    public Label password_lbl;
    public Button submitLogin;
    public Button submitLoginMail;
    public Label errorField1;
    public Label errorField2;

    public String getUsername(){
        return username_fld.getText();
    }
    private void onLogin(){
        Model.getInstance().getViewFactory().showUserUI(username_fld.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        errorField1.setText("");
        errorField2.setText("");


        submitLogin.setOnAction(e-> {
            String username = username_fld.getText();
            String room = room_fld.getText();

        });

        submitLoginMail.setOnAction(e->{
            /* ndad nanak a mehdi */
        });
    }
}
