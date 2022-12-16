package com.socket;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    public enum From{SERVER,CLIENT};
    private String content;
    private LocalDateTime date;
    private From from;
    private String room;
    private String username;

    public Message(String content,From from,String username,String room){
        this.content = content;
        this.from = from;
        this.username = username;
        this.room = room;
        this.date = LocalDateTime.now();
    }
    public LocalDateTime getDate(){return this.date;}
    public String getUsername(){return this.username;}
    public String getRoom(){return this.room;}
    public String getContent(){return this.content;}
}
