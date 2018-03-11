package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReceiveNetworkMessenger extends Thread {

    private static Socket socket;

    public ReceiveNetworkMessenger(Socket socket) {
        this.socket = socket;
    }

    public static ArrayList<Integer> receivemessage() {
        // Read back updated list
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object listObj;
            while ((listObj = ois.readObject()) != null) {
                ArrayList<Integer> list = new ArrayList<>();
                if (listObj instanceof ArrayList<?>) {
                    for (Object object : ((ArrayList<?>) listObj)) {
                        if (object instanceof Integer) {
                            list.add((Integer) object);
                        }
                    }
                    // Show user the list
                    System.out.println("List: " + list);
//                    ois.close();
                    return list;
                }
//                ois.close();
                return new ArrayList<>();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}