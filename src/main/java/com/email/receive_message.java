package com.email;

import com.example.messageriefx.Controllers.User.UserController;
import com.sun.mail.util.MailSSLSocketFactory;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import java.io.*;
import java.security.GeneralSecurityException;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class receive_message {
    private static final String port = "993";
    private static final String host = "imap.gmail.com";
    private static final String protocol = "imap";
    public static Message[] messages;
    public static Message[] mess;
    public static Folder fd;
    public static int index;
    public static boolean checkcontentemail = false;
    public static class HBoxCell extends HBox {
        Label label = new Label();
        Button button = new Button();

        HBoxCell(String labelText, String buttonText,boolean unseeen) {
            super();
            Label label1 = new Label();
            label1.setText(labelText);
            label1.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label1, Priority.ALWAYS);
            Label label2 = new Label();
            label2.setText(String.valueOf(buttonText));
            if(unseeen){
                label1.setStyle("-fx-text-fill: black;-fx-font-size:15");
                label2.setStyle("-fx-text-fill: black;-fx-font-size:15");
            }else{
                label1.setStyle("-fx-text-fill: grey;-fx-font-size:15");
                label2.setStyle("-fx-text-fill: grey;-fx-font-size:15");
            }
            label1.setPrefWidth(100);
            this.getChildren().addAll(label1, label2);
        }
    };
    private String parseAddresses(Address[] address) {

        String listOfAddress = "";
        if ((address == null) || (address.length < 1))
            return null;
        if (!(address[0] instanceof InternetAddress))
            return null;

        for (int i = 0; i < address.length; i++) {
            InternetAddress internetAddress =
                    (InternetAddress) address[0];
            listOfAddress += internetAddress.getAddress()+",";
        }
        return listOfAddress;
    }
    public static String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
    public void getNewEmails(String userName, String password, ListView<HBoxCell> Lists) {
        Properties properties = receive_message_props.getServerProperties(protocol,
                host, port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                MailSSLSocketFactory socketFactory= null;
                try {
                    socketFactory = new MailSSLSocketFactory();
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }
                socketFactory.setTrustAllHosts(true);
                properties.put("mail.imaps.ssl.socketFactory", socketFactory);
                Session session = Session.getDefaultInstance(properties);

                try {
                    Store store = session.getStore(protocol);
                    store.connect(userName, password);
                    Folder inbox = store.getFolder("INBOX");
                    inbox.open(Folder.READ_WRITE);
                    fd = inbox;
                    int c = inbox.getMessageCount();
                    mess = inbox.getMessages(1,c);

                    Flags seen = new Flags(Flags.Flag.SEEN);
                    FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
                    Message messagesunseen[] = inbox.search(unseenFlagTerm);

                    FlagTerm seenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), true);
                    Message messagesseen[] = inbox.search(seenFlagTerm);

                    messages = new Message[messagesseen.length+messagesunseen.length];
                    int pos = 0;
                    for (Message element : messagesunseen) {
                        messages[pos] = element;
                        pos++;
                    }

                    for (Message element : messagesseen) {
                        messages[pos] = element;
                        pos++;
                    }
                    Arrays.sort( messages, (m1, m2 ) -> {
                        try {
                            return m2.getSentDate().compareTo( m1.getSentDate() );
                        } catch ( MessagingException e ) {
                            throw new RuntimeException( e );
                        }
                    } );
                    if(checkcontentemail){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // do your GUI stuff here
                                try {
                                    String messageContent = "";
                                    String attachFiles = "";
                                    System.out.println("i entered");
                                    Address[] fromAddresses = messages[index+1].getFrom();
                                    String fromemail= fromAddresses[0].toString();
                                    String toemail = parseAddresses(messages[index+1].getRecipients(Message.RecipientType.TO));
                                    String ccemails = parseAddresses(messages[index+1].getRecipients(Message.RecipientType.CC));
                                    String subject = messages[index+1].getSubject();
                                    Date sentdate = messages[index+1].getSentDate();
                                    String contentType = messages[index+1].getContentType();

                                    // store attachment file name, separated by comma

                                    if (contentType.contains("multipart")) {
                                        Object content = messages[index+1].getContent();
                                        if (content != null) {
                                            messageContent = content.toString();

                                        }

                                        // content may contain attachments
                                        Multipart multiPart = (Multipart) messages[index+1].getContent();
                                        int numberOfParts = multiPart.getCount();
                                        for (int partCount = 0; partCount < numberOfParts; partCount++) {
                                            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                                System.out.println("xd");
                                                // this part is attachment
                                                String fileName = part.getFileName();
                                                attachFiles += fileName + ", ";
                                                // Do no overwrite existing file
                                                File file = new File(fileName);
                                                for (int j=0; file.exists(); j++) {
                                                    file = new File(fileName+j);
                                                }
                                                part.saveFile("D:/emailattach" + File.separator + fileName);
                                                messageContent = getText(messages[index+1]);  // to get message body of attached emails
                                            }else {
                                                System.out.println("xd1");

                                                // this part for the message content
                                                messageContent = part.getContent().toString();
                                            }

                                        }
                                        if (attachFiles.length() > 1) {
                                            attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                                            UserController.engine.loadContent(attachFiles);
                                        }
                                    } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                                        System.out.println("xd3");
                                        Object content = messages[index+1].getContent();
                                        System.out.println("i entered html file");
                                        if (content != null) {
                                            messageContent = content.toString();
                                        }
                                    }
                                    UserController.engine.loadContent(messageContent);
                                } catch (MessagingException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                checkcontentemail = false;
                            }
                        });
                        return;
                    }
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 1;i<messages.length;i++){
                                try {
                                    int currentYear=messages[i].getSentDate().getYear()+1900;
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(messages[i].getSentDate());
                                    for(int j = 0;j<messagesunseen.length;j++){
                                        if(messages[i]==messagesunseen[j]){
                                            HBoxCell unseenmsg = new HBoxCell(messages[i].getSubject(), cal.get(Calendar.DAY_OF_MONTH) +" "+ new DateFormatSymbols().getMonths()[messages[i].getSentDate().getMonth()-1]+ " " + currentYear+ " " + messages[i].getSentDate().getHours()+":"+messages[i].getSentDate().getMinutes(),true);
                                            unseenmsg.setStyle("-fx-background-color: white;-fx-border-color: white");
                                            Lists.getItems().add(unseenmsg);
                                            break;
                                        }
                                    }
                                    for(int z = 0;z<messagesseen.length;z++){
                                        if(messages[i]==messagesseen[z]){
                                            HBoxCell seenmsg = new HBoxCell(messages[i].getSubject(), cal.get(Calendar.DAY_OF_MONTH) +" "+ new DateFormatSymbols().getMonths()[messages[i].getSentDate().getMonth()-1]+ " " + currentYear+ " " + messages[i].getSentDate().getHours()+":"+messages[i].getSentDate().getMinutes(),false);
                                            seenmsg.setStyle("-fx-background-color: white;-fx-border-color: white");
                                            Lists.getItems().add(seenmsg);
                                            break;
                                        }
                                    }
                                }catch (FolderClosedException a){
                                    System.out.println("folder is closed");
                                }
                                catch (MessagingException e) {
                                    throw new RuntimeException(e);
                                }catch (IndexOutOfBoundsException a){
                                    a.printStackTrace();
                                }
                            }
                        }
                    };
                    Thread thread_showing_messages = new Thread(r);
                    thread_showing_messages.start();
                    thread_showing_messages.join();
                    inbox.close(false);
                    store.close();
                } catch (NoSuchProviderException ex) {
                    System.out.println("No provider for protocol: "
                            + protocol);
                    ex.printStackTrace();
                } catch (MessagingException ex) {
                    System.out.println("Could not connect to the message store");
                    ex.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
