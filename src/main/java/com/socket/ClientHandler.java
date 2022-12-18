package com.socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket socket;
    private String username;
    private String room;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public ClientHandler(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());

        // Read client username
        Demande d1 = (Demande) objectInputStream.readObject();
        username = (String) d1.getNewValue();

        // Read room id
        Demande d2 = (Demande) objectInputStream.readObject();
        room = (String) d2.getNewValue();

        // Add client to the clients list
        clientHandlers.add(this);
    }

    /**
     * Send message
     * @param message Message
     * @return Returns true if the message was sent successfully
     */
    public boolean sendMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
            return true;
        } catch (IOException e) {
            close();
        }

        return false;
    }

    public void broadcastMessage(Message message) {
        if (message.getRoom() == null) {
            // Send message to all clients
            for (ClientHandler c : clientHandlers) {
                if (c != this) {
                    c.sendMessage(message);
                }
            }
        } else {
            // Send message to the room
            for (ClientHandler c : clientHandlers) {
                if (c != this && c.getRoom().equals(this.room)) {
                    c.sendMessage(message);
                }
            }
        }
    }

    @Override
    public void run() {
        broadcastMessage(new Message("server", username + " joins the room", room, Message.From.SERVER));

        Transfert transferredContent;

        while (socket.isConnected()) {
            try {
                transferredContent = (Transfert) objectInputStream.readObject();

                if (transferredContent instanceof Message) {
                    broadcastMessage((Message) transferredContent);
                }

                if (transferredContent instanceof Demande) {
                    String newValue = (String) ((Demande) transferredContent).getNewValue();

                    switch (((Demande) transferredContent).getType()) {
                        case SET_ROOM -> setRoom(newValue);
                        case SET_USERNAME -> setUsername(newValue);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                close();
                break;
            }
        }
    }

    public void close() {
        clientHandlers.remove(this);
        broadcastMessage(new Message("server", username + " leaves the room", room, Message.From.SERVER));

        try {
            if (objectInputStream != null)
                objectInputStream.close();
            if (objectOutputStream != null)
                objectOutputStream.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Message createMessage(String msg) {
        return new Message(username, msg, room, Message.From.SERVER);
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
}
