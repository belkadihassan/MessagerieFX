package com.example.messageriefx.Controllers;

import com.example.messageriefx.Controllers.User.UserController;
import com.example.messageriefx.Models.ClientModel;
import com.example.messageriefx.Models.Con_Stmt;
import com.example.messageriefx.Models.Model;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label username_lbl;
    public TextField username_fld;
    public Label password_lbl;
    public PasswordField password_fld;
    public Hyperlink signINLink;
    public Button submitLogin;
    public Label errorField;
    public static String username;
    public static String password;
    public String getUsername(){
        return username_fld.getText();
    }
    private void onLogin(){
        Model.getInstance().getViewFactory().showUserUI(username_fld.getText());
    }
    public void user_exist() throws SQLException, SocketException, UnknownHostException {
        ClientModel client = new ClientModel();
        ResultSet rss = client.userExist_SELECT();
        rss.next();
        if(!SignINController.detect_Mac().equals(client.verify_Mac())){
            errorField.setText("your not the real user");
            return;
        }
        if(rss.getInt(1)>=1){
            Stage stage = (Stage) errorField.getScene().getWindow();
            onLogin();
            Model.getInstance().getViewFactory().closeStage(stage);
        }else{
            errorField.setText("Password incorrect, try again!!");
        }
        rss.close();
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
            username = username_fld.getText();
            password = password_fld.getText();
            /*if(IDandPassword.getLoginInfo().containsKey(username)){
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
            }*/
            try {
                String ur = "jdbc:mysql://localhost:3306/singin";
                Con_Stmt.con = DriverManager.getConnection(ur,"root","");
                /*PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM clients WHERE PSEUDO = ? AND PASSWORD = ?");
                stmt.setString(1,username);
                stmt.setString(2,password);
                ResultSet rss = stmt.executeQuery();
                rss.next();
                if(rss.getInt(1)>=1){
                    Stage stage = (Stage) errorField.getScene().getWindow();
                    onLogin();
                    Model.getInstance().getViewFactory().closeStage(stage);
                }else{
                    errorField.setText("Password incorrect, try again!!");
                }*/
                user_exist();
                Con_Stmt.stmt.close();
                Con_Stmt.con.close();
            }catch (Exception exp){
                exp.printStackTrace();
            }
        });
    }
}
