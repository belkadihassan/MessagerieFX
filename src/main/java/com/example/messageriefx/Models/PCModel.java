package com.example.messageriefx.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PCModel extends AbstractPcModel{

    @Override
    public int getID_PC() {
        return this.ID_PC;
    }

    @Override
    public String getMAC() {
        return this.MAC;
    }


    @Override
    public void setID_PC(int ID_PC) {
        this.ID_PC = ID_PC;
    }

    @Override
    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    @Override
    public int User_MAC() throws SQLException {
        Con_Stmt.stmt = Con_Stmt.con.prepareStatement("SELECT COUNT(*) FROM pc WHERE MAC = ?");
        Con_Stmt.stmt.setString(1,this.getMAC());
        ResultSet rs = Con_Stmt.stmt.executeQuery();
        rs.next();
        int a = rs.getInt(1);
        System.out.println(a);
        if(a == 0){
            Con_Stmt.stmt = Con_Stmt.con.prepareStatement("INSERT INTO pc (MAC)VALUES(?)");
            Con_Stmt.stmt.setString(1,this.getMAC());
            Con_Stmt.stmt.executeUpdate();
            Con_Stmt.stmt = Con_Stmt.con.prepareStatement("SELECT * FROM pc WHERE MAC = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Con_Stmt.stmt.setString(1,this.getMAC());
            ResultSet rss = Con_Stmt.stmt.executeQuery();
            rss.last();
            System.out.println("first time");
            return rss.getInt(1);
        }else{
            System.out.println("second time");
            Con_Stmt.stmt = Con_Stmt.con.prepareStatement("SELECT * FROM pc WHERE MAC = ?");
            Con_Stmt.stmt.setString(1,this.getMAC());
            ResultSet rss = Con_Stmt.stmt.executeQuery();
            rss.next();
            return rss.getInt(1);
        }
    }
}
