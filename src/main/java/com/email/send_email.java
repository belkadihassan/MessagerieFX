package com.email;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class send_email implements Runnable {
    public static final String host = "smtp.gmail.com";
    public static final String port = "465";
    public static boolean conenction = false;
    public send_email(String from,String pwd,String to){
        try{
            Email_send_properties session = new Email_send_properties();
            Message message = new MimeMessage(session.create_session(host,port,from,pwd));
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler," + "\n\n No spam to my email, please!");
                    System.out.println("connecting...");
                    try {
                        Transport.send(message);
                        conenction = true;
                    } catch (MessagingException e) {
                        if(conenction){
                            System.out.println("connected succefumly to avoid warning");
                        }else{
                            System.out.println("account doest exist");
                            throw new RuntimeException(e);
                        }
                    }
            System.out.println("connection succes");

        }catch (AddressException a){
            System.out.println("adress n'exist pas");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }catch (Exception a){
            System.out.println("username or password doesnt exist");
        }
    }

    @Override
    public void run() {

    }
}
