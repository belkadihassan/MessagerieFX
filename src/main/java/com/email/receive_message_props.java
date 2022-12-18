package com.email;

import java.util.Properties;

public class receive_message_props {
    public static Properties getServerProperties(String protocol,
                                           String host, String port) {
        Properties properties = new Properties();
        properties.put(String.format("mail.%s.host",
                protocol), host);
        properties.put(String.format("mail.%s.port",
                protocol), port);
        properties.setProperty(
                String.format("mail.%s.socketFactory.class",
                        protocol), "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback",
                        protocol), "false");

        properties.setProperty(
                String.format("mail.%s.socketFactory.port",
                        protocol), String.valueOf(port));
        properties.put("mail.imap.ssl.protocols", "TLSv1.2");
        return properties;
    }
}
