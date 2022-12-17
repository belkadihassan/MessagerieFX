package com.example.messageriefx.Models;

import java.sql.SQLException;

abstract class AbstractPcModel {
    public int ID_PC;
    public String MAC;

    public abstract int getID_PC();

    public abstract String getMAC();
    public abstract int User_MAC() throws SQLException;

    public abstract void setID_PC(int ID_PC);

    public abstract void setMAC(String MAC);

}
