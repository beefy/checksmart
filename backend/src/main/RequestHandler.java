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

    private Socket clientSocket1 = null;
    private Socket clientSocket2 = null;


    Socket readingclient = null;
    Socket writingclient = null;

    public RequestHandler(Socket client1, Socket client2) {
        clientSocket1 = client1;
        clientSocket2 = client2;

        readingclient = client2;
        writingclient = client1;
    }

    public void run() {

        ArrayList<Integer> intArrayList = new ArrayList<>();
        Object obj = null;
        try {
            // we're reading from the client that's writing to us
            ObjectInputStream is = new ObjectInputStream(writingclient.getInputStream());
            while ((obj = is.readObject()) != null) {
                if (obj instanceof ArrayList<?>) {
                    for (Object object : ((ArrayList<?>) obj)) {
                        if (object instanceof Integer) {
                            intArrayList.add((Integer) object);
                        }
                    }
                }

                try {
                    // we write to the client thats reading for us
                    ObjectOutputStream os = new ObjectOutputStream(readingclient.getOutputStream());
                    os.writeObject(intArrayList);
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // swap the reading and writing clients to switch the game turn
        Socket tmpsocket = writingclient;
        writingclient = readingclient;
        readingclient = tmpsocket;
    }

}