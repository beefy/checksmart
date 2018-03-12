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

    interface Callback {
        void onSuccess(ArrayList<Integer> nextmove);
    }

    public static void receivemessage(final Callback callback) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
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
                            callback.onSuccess(list);
                            return;
                        }
//                ois.close();
                        callback.onSuccess(new ArrayList<>());
                        return;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(new ArrayList<>());
                return;
            }
        });
        t.start();
    }
}