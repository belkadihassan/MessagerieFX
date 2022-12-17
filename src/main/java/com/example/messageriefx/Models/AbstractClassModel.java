package com.example.messageriefx.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract class AbstractClassModel {
    public int ID;
    public String PSEUDO;
    public String PASSWORD;

    public abstract int getID();
    public abstract String getPASSWORD();
    public abstract String getPSEUDO( );
    public abstract void setID(int ID);
    public abstract void setPSEUDO(String ID);
    public abstract void setPASSWORD(String ID);
    public abstract void add_acount(int PC_ID) throws SQLException;
    public abstract int Unique_User() throws SQLException;
    public abstract ResultSet userExist_SELECT() throws SQLException;

    public abstract int Unique_Password() throws SQLException;
    public abstract String verify_Mac() throws SQLException;
    public abstract void DELETE();
    public abstract void UPDATE();
}
