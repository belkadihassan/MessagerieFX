package com.example.messageriefx.Controllers.email;

import javafx.scene.control.Label;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Properties;

public class RetrieveEmailsUsingIMAP {

    private static Properties getServerProperties(String protocol, String host, String port) {
        System.out.println("Connecting please wait...");
        Properties properties = new Properties();
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
        properties.setProperty(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
        properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));
        properties.put("mail.imap.ssl.protocols", "TLSv1.2");
        return properties;
    }

    public String receivemessage(String protocol, String host, String port, String userName, String password, int index) throws IOException {

        System.out.println("Inside getEmails method...");
        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);
        System.out.println("Connected to: "+host);
        String messageContent = "";
        String attachFiles = "";
        try {
            Store store = session.getStore(protocol);
            store.connect(userName, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            /*int count = inbox.getMessageCount();
            System.out.println("You have "+count+" emails in your inbox.");
            Message[] messages = inbox.getMessages(1, count);
            System.out.println(receive_message.messages.length);*/
            FileInputStream filee = new FileInputStream("Person.bin");
            ObjectInputStream in = new ObjectInputStream(filee);
            Message[] P = (Message[]) in.readObject();
            filee.close();
            in.close();
            for (int i=0; i<receive_message.mess.length;i++) {
                Message message = P[i];
                if(receive_message.messages[index+1].getSentDate().equals(message.getSentDate()) && receive_message.messages[index+1].getSubject().equals(message.getSubject())){
                    System.out.println("i entered");
                    Address[] fromAddresses = message.getFrom();
                    String fromemail= fromAddresses[0].toString();
                    String toemail = parseAddresses(message.getRecipients(Message.RecipientType.TO));
                    String ccemails = parseAddresses(message.getRecipients(Message.RecipientType.CC));
                    String subject = message.getSubject();
                    Date sentdate = message.getSentDate();

                    String contentType = message.getContentType();

                    // store attachment file name, separated by comma

                    if (contentType.contains("multipart")) {
                        Object content = message.getContent();
                        if (content != null) {
                            messageContent = content.toString();

                        }

                        // content may contain attachments
                        Multipart multiPart = (Multipart) message.getContent();
                        int numberOfParts = multiPart.getCount();
                        for (int partCount = 0; partCount < numberOfParts; partCount++) {
                            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                // this part is attachment
                                String fileName = part.getFileName();
                                attachFiles += fileName + ", ";
                                // Do no overwrite existing file
                                File file = new File(fileName);
                                for (int j=0; file.exists(); j++) {
                                    file = new File(fileName+j);
                                }
                                part.saveFile("D:/emailattach" + File.separator + fileName);
                                messageContent = getText(message);  // to get message body of attached emails
                            }else {
                                // this part for the message content
                                messageContent = part.getContent().toString();
                            }

                        }
                        if (attachFiles.length() > 1) {
                            attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                            return attachFiles;
                        }
                    } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                        Object content = message.getContent();
                        System.out.println("i entered html file");
                        if (content != null) {
                            messageContent = content.toString();
                            System.out.println(messageContent);
                        }
                    }
                }
            }

            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: "
                    + protocol);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return messageContent;
    }

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


    public String getConnectedStatus(String protocol, String host, String port, String userName, String password){
        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);
        String isconnected="";
        try {
            //---- Start Connection Establishment----------
            Store store = session.getStore(protocol);
            System.out.println("Connecting to email....");
            store.connect(userName, password);
            isconnected = "Connected_to_IMAP";
            System.out.println("Is Connected: "+isconnected);
            System.out.println("Connected to :"+protocol);
        } catch (NoSuchProviderException ex) {
            String ex1 = "No provider for protocol: "+ protocol;
            System.out.println(ex1);
            return ex1;
        } catch (MessagingException ex) {
            String ex2 = "Could not connect to the message store";
            System.out.println(ex2);
            return ex2;
        }
        return isconnected;
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


}
