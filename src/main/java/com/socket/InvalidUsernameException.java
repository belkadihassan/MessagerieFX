package com.socket;

public class InvalidUsernameException extends Exception {
    private String username;

    public InvalidUsernameException() {
        super("Invalid username !");
    }

    public InvalidUsernameException(String username) {
        super("Username `" + username + "` invalid !");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
