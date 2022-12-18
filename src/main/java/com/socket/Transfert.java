package com.socket;

import java.io.Serializable;

public class Transfert implements Serializable {
    protected String username;
    protected String room;

    public Transfert(String username, String room) {
        this.username = username;
        this.room = room;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nRoom: " + room;
    }
}
