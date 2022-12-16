package com.example.messageriefx.Controllers;

public class Session {

    private static String currentUser , email;
    public Session(){
        currentUser = "";
        email = "";
    }
    public static void startSession(String user){
        setCurrentUser(user);
    }
    public static void setCurrentUser(String user){currentUser = user;}
    public static void setEmail(String email){email = email;}

    public static String getCurrentUser(){return currentUser;}
    public static String getEmail(){return email;}
    public static void endSession(){
        currentUser = "";
        email = "";
    }
}
