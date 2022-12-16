package com.socket;

import java.util.ArrayList;

public class Client {
    private String username;
    private ArrayList<Client> blockedUsers;
    public Client(String username){
        this.username = username;
    }
    public String getUsername(){return username;}
    ArrayList<Client> getBlockedUsers(){return blockedUsers;}
    public void blockUser(Client user){
        blockedUsers.add(user);
    }
}
