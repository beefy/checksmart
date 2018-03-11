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
            Socket client;
            try {
                client = serverSocket.accept();
                RequestHandler handler = new RequestHandler(client);
                handler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
    }

}

