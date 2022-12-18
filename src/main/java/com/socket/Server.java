package com.socket;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept(); // Waiting for client connexion
                ClientHandler clientHandler = new ClientHandler(socket);
                new Thread(clientHandler).start();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("error: " + e.getMessage());
            end();
        }
    }

    public void end() {
        try {
            if (this.serverSocket != null)
                this.serverSocket.close();
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server s = null;
        try {
            s = new Server(new ServerSocket(1111));
            s.start();
        } catch (IOException e) {
            if (s != null) s.end();
            System.out.println("error: " + e.getMessage());
        }
    }
}
