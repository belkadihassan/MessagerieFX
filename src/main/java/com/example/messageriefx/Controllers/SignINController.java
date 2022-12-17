package com.example.messageriefx.Controllers;

import com.example.messageriefx.Models.ClientModel;
import com.example.messageriefx.Models.Con_Stmt;
import com.example.messageriefx.Models.Model;
import com.example.messageriefx.Models.PCModel;
import com.socket.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.*;
import java.sql.*;

import java.util.ResourceBundle;

public class SignINController implements Initializable {
    public TextField username_fld;
    public PasswordField password_fld;
    public Label password_lbl;
    public PasswordField passwordCheck_fld;
    public Button submitSignIN;
    public Hyperlink loginLink;
    public Label error;
    public static String detect_Mac() throws SocketException, UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();
        System.out.println(mac);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }
    public void account_already_exist() throws SQLException, SocketException, UnknownHostException {
        ClientModel client = new ClientModel();
        client.setPSEUDO(username_fld.getText());
        client.setPASSWORD(password_fld.getText());
        if(client.Unique_User()>=1 || client.Unique_Password()>=1){
            error.setText("your Account name or password already exist");
        }else{
            PCModel Pc = new PCModel();
            Pc.setMAC(SignINController.detect_Mac());
            client.add_acount(Pc.User_MAC());
            Stage stage = (Stage) error.getScene().getWindow();
            Model.getInstance().getViewFactory().showLoginFrame();
            Model.getInstance().getViewFactory().closeStage(stage);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error.setText("");
        loginLink.setOnAction(e-> {

            Stage stage = (Stage) error.getScene().getWindow();

            Model.getInstance().getViewFactory().showLoginFrame();
            Model.getInstance().getViewFactory().closeStage(stage);
        });
        submitSignIN.setOnAction(e->{
            /*if(username_fld.getText().equals("") || (password_fld.getText().equals(""))){
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
            }*/
            if(username_fld.getText().equals("") || (password_fld.getText().equals(""))){
                error.setText("Username or password field empty !!");
                return;
            }
            if(password_fld.getText().length()<8){
                password_lbl.setText("password (at least 8 chars !!)");
                error.setText("the password is too short");
                return;
            }
            try {
                String ur = "jdbc:mysql://localhost:3306/singin";
                Con_Stmt.con = DriverManager.getConnection(ur,"root","");
                account_already_exist();
                Con_Stmt.stmt.close();
                Con_Stmt.con.close();
            }catch (Exception exp){
                exp.printStackTrace();
            }
            IDandPassword newPersone = new IDandPassword(username_fld.getText() , password_fld.getText());


        });
    }
}
