package com.socket;

public class Demande extends Transfert {
    public enum DemandeType {
        SET_USERNAME,
        SET_ROOM,
    }

    private DemandeType type;
    private Object newValue;

    public Demande(String username, String room, DemandeType type, Object newValue) {
        super(username, room);
        this.newValue = newValue;
        this.type = type;
    }

    public DemandeType getType() {
        return type;
    }

    public Object getNewValue() {
        return newValue;
    }
}
