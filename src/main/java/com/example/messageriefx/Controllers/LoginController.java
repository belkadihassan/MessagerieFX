package com.example.messageriefx.Controllers;

import com.example.messageriefx.Controllers.User.UserController;
import com.example.messageriefx.Models.Model;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label username_lbl;
    public TextField username_fld;
    public Label password_lbl;
    public PasswordField password_fld;
    public Hyperlink signINLink;
    public Button submitLogin;
    public Label errorField;

    public String getUsername(){
        return username_fld.getText();
    }
    private void onLogin(){
        Model.getInstance().getViewFactory().showUserUI(username_fld.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        errorField.setText("");

        IDandPassword idp2 = new IDandPassword("hassan" , "ehh");
        IDandPassword idp3 = new IDandPassword("afkir" , "zekek");

        signINLink.setOnAction(e->{
            Stage stage = (Stage) errorField.getScene().getWindow();
            Model.getInstance().getViewFactory().showSignINFrame();
            Model.getInstance().getViewFactory().closeStage(stage);
        });

        submitLogin.setOnAction(e-> {
            String username = username_fld.getText();
            String password = password_fld.getText();
            if(IDandPassword.getLoginInfo().containsKey(username)){
                String correctPassword = (String) IDandPassword.getLoginInfo().get(username);
                if(password.equals(correctPassword)){
                    Stage stage = (Stage) errorField.getScene().getWindow();
                    onLogin();
                    Model.getInstance().getViewFactory().closeStage(stage);
                }else {
                    errorField.setText("Password incorrect, try again!!");
                }
            }
            else {
                errorField.setText("user not found !!");
            }
        });
    }
}
