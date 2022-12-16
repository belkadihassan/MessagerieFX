package com.example.messageriefx.Controllers;

import java.util.HashMap;

public class IDandPassword {
    private static HashMap<String,String> loginInfo = new HashMap<>();
    public IDandPassword(String username , String password){
        loginInfo.put(username,password);
    }
    public static HashMap getLoginInfo(){return loginInfo;}
}
