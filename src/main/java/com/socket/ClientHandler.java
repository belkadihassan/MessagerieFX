package com.socket;

import com.example.messageriefx.Controllers.Session;
import com.example.messageriefx.Controllers.User.UserController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String username;
    private String room;
    public ClientHandler(Socket socket){

        try{
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());

            this.username = Session.getCurrentUser();
            clientHandlers.add(this);
            broadcastMessage(new Message("SERVER : "+this.username+ " has entered the chat",Message.From.SERVER,username,"world cup"));
        }catch (IOException e){
            closeEverything(socket,objectInputStream,objectOutputStream);
        }
    }

    @Override
    public void run() {
        Message messageFromClient;
        while (socket.isConnected()){
            try{
                messageFromClient = (Message) objectInputStream.readObject();

            } catch (IOException e) {
                closeEverything(socket,objectInputStream,objectOutputStream);
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void broadcastMessage(Message message){
        for(ClientHandler clientHandler:clientHandlers){
            try {
                if(!clientHandler.username.equals(this.username)){
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                }
            }
            catch(IOException e){
                closeEverything(socket,objectInputStream,objectOutputStream);
            }
        }
    }
    public void removeClientHandler(){
        clientHandlers.remove(this);
        String message = "SERVER : " + this.username + " has left the chat";
        broadcastMessage(new Message(message , Message.From.SERVER , username,"world cup"));
    }
    public void closeEverything(Socket socket,ObjectInputStream ois,ObjectOutputStream oos){
        removeClientHandler();
        try{
            if (socket!=null) socket.close();
            if (ois!=null) ois.close();
            if(oos!=null) oos.close();
        }catch (IOException e){
            e.getStackTrace();
        }
    }
}
