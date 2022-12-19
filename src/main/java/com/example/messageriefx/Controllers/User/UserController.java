package com.example.messageriefx.Controllers.User;

import com.example.messageriefx.Controllers.LoginController;
import com.example.messageriefx.Controllers.Session;
import com.email.receive_message;
import com.email.send_email;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.mail.Address;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    public VBox foldersBox;
    public ImageView refresh;
    public ListView messagesList;
    public WebView webView;
    public Pane childPane;
    public Pane parentPane;
    public Label dateLabel;
    public Label fromLabel;
    public Label recent;        /*recent Messages*/
    public Button backToMessages_btn;
    private String username;
    public static WebEngine engine;
    public static String to = "almahdi.achbab@etu.uae.ac.ma";

    public void setUsername(String username) {this.username = username;}
    public String getUsername(){return username;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        receive_message rm = new receive_message();
        rm.getNewEmails(LoginController.mailuser,LoginController.passuser,messagesList);

        backToMessages_btn.setVisible(false);
        childPane.setVisible(false);

        childPane.setVisible(false);
        backToMessages_btn.setOnAction(e->{
            childPane.setVisible(false);
            backToMessages_btn.setVisible(false);
        });

        /*addMailBtn.setOnAction(e-> {
            Session.setEmail(inputMail.getText());
            Thread account_sender = new Thread(new send_email(inputMail.getText(),inputPsMail.getText(),to));
            try {
                account_sender.start();
                account_sender.join();
                if(!send_email.conenction){
                    addMailError.setVisible(true);
                    addMailError.setText("username or password is not coàrrect");
                }else{
                    receive_message rm = new receive_message();
                    rm.getNewEmails(inputMail.getText(),inputPsMail.getText(),messagesList);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });*/

    }
    public void clicking_receiving_message(MouseEvent mouseEvent) throws IOException, MessagingException {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                recent.setVisible(false);
                messagesList.setVisible(false);
                childPane.setVisible(true);
                receive_message rm = new receive_message();
                engine = webView.getEngine();
                receive_message.index = messagesList.getSelectionModel().getSelectedIndex();
                receive_message.checkcontentemail = true;
                Address[] fromAddresses = receive_message.messages[receive_message.index+1].getFrom();
                String fromemail= fromAddresses[0].toString();
                //rm.getNewEmails(inputMail.getText(),inputPsMail.getText(),messagesList);
                fromLabel.setText(fromemail);
                dateLabel.setText(String.valueOf(receive_message.messages[receive_message.index+1].getSentDate().getHours()+":"+receive_message.messages[receive_message.index+1].getSentDate().getMinutes()));
            }
        }
    }
}
