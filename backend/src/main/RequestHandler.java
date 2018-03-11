package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class RequestHandler extends Thread {

    final static String IP_ADDRESS = "localhost";
    final static int PORT = 1066;
    final static int TIMEOUT = 10000;

    private Socket clientSocket = null;

    public RequestHandler(Socket _clientSocket) {
        clientSocket = _clientSocket;
    }

    public void run() {

        ArrayList<Integer> intArrayList = new ArrayList<>();
        Object obj = null;
        try {
            ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
            while ((obj = is.readObject()) != null) {
                if (obj instanceof ArrayList<?>) {
                    for (Object object : ((ArrayList<?>) obj)) {
                        if (object instanceof Integer) {
                            // Add to return list if it is even
                            if (((Integer) object) % 2 != 0) {
                                intArrayList.add((Integer) object);
                            }
                        }
                    }
                }
                break;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
            os.writeObject(intArrayList);
            os.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}