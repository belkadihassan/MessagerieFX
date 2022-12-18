package com.socket;

import java.io.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    private final Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String username;
    private String room;
    private static final ArrayList<String> BANNED_USERNAMES = new ArrayList<>(Arrays.asList("server", "admin"));

    public Client(Socket socket, String username, String room) throws IOException, InvalidUsernameException {
        this.socket = socket;
        this.setUsername(username);
        this.setRoom(room);
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());

        // Send username to server
        objectOutputStream.writeObject(new Demande(username, room, Demande.DemandeType.SET_USERNAME, username));
        // Send room to server
        objectOutputStream.writeObject(new Demande(username, room, Demande.DemandeType.SET_ROOM, room));
    }

    /**
     * Send message
     * @param msg Message content
     * @return
     */
    public Message sendMessage(String msg) {
        if (socket.isConnected()) {
            try {
                // Send message
                Message message = createMessage(msg);
                objectOutputStream.writeObject(message);
                return message;
            } catch (IOException e) {
                close();
            }
        }

        return null;
    }

    /**
     * Receive message sent from message
     * @param listener callback
     */
    public void receiveMessage(MessageListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        // Read message sent from server
                        Message message = (Message) objectInputStream.readObject();
                        listener.listen(message);
                    } catch (IOException | ClassNotFoundException e) {
                        close();
                        break;
                    }
                }
            }
        }).start();
    }

    private Message createMessage(String message) {
        return new Message(username, message, room, Message.From.CLIENT);
    }

    private static boolean checkUsername(String username)    {
        return !BANNED_USERNAMES.contains(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws InvalidUsernameException {
        if (checkUsername(username)) {
            this.username = username;
        } else {
            throw new InvalidUsernameException(username);
        }
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Close socket client
     */
    public void close() {
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
}
