package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class SendNetworkMessenger {

    private static Socket socket;

    public SendNetworkMessenger(Socket socket) {
        this.socket = socket;
    }

    public static void sendmessage(Object msg) {
        try {
            ObjectOutputStream obs = new ObjectOutputStream(socket.getOutputStream());
            // Send list to server to be filtered
            obs.writeObject(msg);
            obs.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
