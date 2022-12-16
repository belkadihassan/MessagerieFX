package com.example.messageriefx.Controllers;

import com.example.messageriefx.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignINController implements Initializable {
    public TextField username_fld;
    public PasswordField password_fld;
    public Label password_lbl;
    public PasswordField passwordCheck_fld;
    public Button submitSignIN;
    public Hyperlink loginLink;
    public Label error;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error.setText("");
        loginLink.setOnAction(e-> {

            Stage stage = (Stage) error.getScene().getWindow();

            Model.getInstance().getViewFactory().showLoginFrame();
            Model.getInstance().getViewFactory().closeStage(stage);
        });
        submitSignIN.setOnAction(e->{
            if(username_fld.getText().equals("") || (password_fld.getText().equals(""))){
                error.setText("Username or password field empty !!");
                return;
            }
            if(!passwordCheck_fld.getText().equals(password_fld.getText())){
                error.setText("Password incorrect !!");
                passwordCheck_fld.setStyle("-fx-outer-border : #f00");
                return;
            }
            if (IDandPassword.getLoginInfo().containsKey(username_fld.getText())){
                error.setText("username is already used");
                return;
            }
            if(password_fld.getText().length()<8){
                password_lbl.setText("password (at least 8 chars !!)");
                error.setText("the password is too short");
                return;
            }
            IDandPassword newPersone = new IDandPassword(username_fld.getText() , password_fld.getText());

            Stage stage = (Stage) error.getScene().getWindow();

            Model.getInstance().getViewFactory().showLoginFrame();
            Model.getInstance().getViewFactory().closeStage(stage);
        });
    }
}
