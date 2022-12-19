package com.email;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class Email_send_properties {
    public Session create_session(String host,String port,String from,String pwd){
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, pwd);

            }

        });
        return session;
    }
}
