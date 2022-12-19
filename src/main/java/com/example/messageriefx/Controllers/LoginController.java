package com.example.messageriefx.Controllers;


import com.email.send_email;
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
    public static String  mailuser;
    public static String passuser;
    public PasswordField mailPassword_fld;
    public Label password_lbl;
    public Button submitLogin;
    public Button submitLoginMail;
    public Label errorField1;
    public Label errorField2;
    public static String to = "almahdi.achbab@etu.uae.ac.ma";

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
        mailuser = String.valueOf(email_fld.getText());
        passuser = String.valueOf(mailPassword_fld.getText());

        submitLogin.setOnAction(e-> {
            String username = username_fld.getText();
            String room = room_fld.getText();

        });

        submitLoginMail.setOnAction(e->{
            /* ndad nanak a mehdi */
            Session.setEmail(email_fld.getText());
            Thread account_sender = new Thread(new send_email(email_fld.getText(),mailPassword_fld.getText(),to));
            try {
                account_sender.start();
                account_sender.join();
                if(!send_email.conenction){
                    errorField1.setVisible(true);
                    errorField1.setText("username or password is not coàrrect");
                }else{
                    onLogin();
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
