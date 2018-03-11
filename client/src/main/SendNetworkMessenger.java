package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class SendNetworkMessenger {

    final static String IP_ADDRESS = "localhost";
    final static int PORT = 1066;

    public static void sendmessage(ArrayList<Integer> initialList) {
        try {

            Socket socket = new Socket(IP_ADDRESS, PORT);
            ObjectOutputStream obs = new ObjectOutputStream(socket.getOutputStream());
            // Send list to server to be filtered
            obs.writeObject(initialList);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
