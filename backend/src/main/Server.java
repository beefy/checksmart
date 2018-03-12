package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    final static int PORT = 1066;
    final static int TIMEOUT = 10000;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket client1 = null;
            Socket client2 = null;
            while(client1 == null || client2 == null) {
                if(client1 == null) {
                    try {
                        client1 = serverSocket.accept();
                        System.out.println("new client1 accepted: " + client1.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(client2 == null) {
                    try {
                        client2 = serverSocket.accept();
                        System.out.println("new client2 accepted: " + client2.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            RequestHandler handler = new RequestHandler(client1,client2);
            handler.start();
        }
    }

}

