package com.socket;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message extends Transfert {
    private static final long serialVersionUID = -5399605122490343339L;

    public enum From { SERVER, CLIENT }

    private String message;

    private From from;

    private LocalDateTime date;

    public String getMessage() {
        return message;
    }

    public Message(String username, String message, String room, From from) {
        this(username, message, room, from, LocalDateTime.now());
    }

    public Message(String username, String message, String room, From from, LocalDateTime date) {
        super(username, room);
        this.message = message;
        this.from = from;
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public From getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return "Username: " + username
                + "\nRoom: " + room
                + "\nMessage" + message
                + "\nSend date: " + date
                + "\nFrom: " + from;
    }
}
