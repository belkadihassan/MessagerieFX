package com.example.messageriefx.Models;

import com.example.messageriefx.Controllers.LoginController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientModel extends AbstractClassModel{

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getPASSWORD() {
        return this.PASSWORD;
    }

    @Override
    public String getPSEUDO() {
        return this.PSEUDO;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void setPSEUDO(String pseudo) {
        this.PSEUDO = pseudo;
    }

    @Override
    public void setPASSWORD(String password) {
        this.PASSWORD = password;
    }

    @Override
    public void add_acount(int PC_ID) throws SQLException {
        Con_Stmt.stmt = Con_Stmt.con.prepareStatement("INSERT INTO clients (PSEUDO,PASSWORD,ID_PC)VALUES(?,?,?)");
        Con_Stmt.stmt.setString(1,this.getPSEUDO());
        Con_Stmt.stmt.setString(2,this.getPASSWORD());
        Con_Stmt.stmt.setInt(3,PC_ID);
        Con_Stmt.stmt.executeUpdate();
    }

    @Override
    public int Unique_User() throws SQLException {
        Con_Stmt.stmt = Con_Stmt.con.prepareStatement("SELECT COUNT(*) FROM clients WHERE PSEUDO = ?");
        Con_Stmt.stmt.setString(1,this.getPSEUDO());
        ResultSet rs = Con_Stmt.stmt.executeQuery();
        rs.next();
        int a = rs.getInt(1);
        rs.close();
        return a;
    }
    @Override
    public int Unique_Password() throws SQLException {
        Con_Stmt.stmt = Con_Stmt.con.prepareStatement("SELECT COUNT(*) FROM clients WHERE PASSWORD = ?");
        Con_Stmt.stmt.setString(1,this.getPASSWORD());
        ResultSet rs = Con_Stmt.stmt.executeQuery();
        rs.next();
        int a = rs.getInt(1);
        rs.close();
        return a;
    }

    @Override
    public String verify_Mac() throws SQLException {
        Con_Stmt.stmt = Con_Stmt.con.prepareStatement("SELECT P.MAC FROM pc P,clients C WHERE C.ID_PC = P.ID AND C.PASSWORD = ? AND C.PSEUDO = ?");
        Con_Stmt.stmt.setString(1,LoginController.password);
        Con_Stmt.stmt.setString(2,LoginController.username);
        ResultSet rs = Con_Stmt.stmt.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    @Override
    public void DELETE() {

    }

    @Override
    public void UPDATE() {

    }

    @Override
    public ResultSet userExist_SELECT() throws SQLException {
        Con_Stmt.stmt = Con_Stmt.con.prepareStatement("SELECT COUNT(*) FROM clients WHERE PSEUDO = ? AND PASSWORD = ?");
        Con_Stmt.stmt.setString(1, LoginController.username);
        Con_Stmt.stmt.setString(2,LoginController.password);
        ResultSet rs = Con_Stmt.stmt.executeQuery();
        return rs;
    }
}
