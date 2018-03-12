package main;

import java.io.IOException;
import java.io.EOFException;
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

        // send player numbers to client
        try {
            ArrayList<Integer> player1list = new ArrayList<Integer>();
            ArrayList<Integer> player2list = new ArrayList<Integer>();
            player1list.add(1);
            player2list.add(2);
            // tell player 1
            ObjectOutputStream os1 = new ObjectOutputStream(clientSocket1.getOutputStream());
            os1.writeObject(player1list);
            System.out.println("Sent player number to client 1");
            os1.flush();
            // tell player 2
            ObjectOutputStream os2 = new ObjectOutputStream(clientSocket2.getOutputStream());
            os2.writeObject(player2list);
            System.out.println("Sent player number to client 2");
            os2.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while(true) {
            System.out.println("reading from " + writingclient.toString() + " and writing to " + readingclient.toString());

            ArrayList<Integer> intArrayList = new ArrayList<>();
            Object obj = null;
            try {
                // we're reading from the client that's writing to us
                ObjectInputStream is = new ObjectInputStream(writingclient.getInputStream());
                while ((obj = is.readObject()) != null) {
                    if (obj instanceof ArrayList<?>) {
                        // for game play
                        for (Object object : ((ArrayList<?>) obj)) {
                            if (object instanceof Integer) {
                                intArrayList.add((Integer) object);
                            }
                        }
                    }
                    System.out.println("received data: " + intArrayList.toString());

                    try {
                        // we write to the client thats reading for us
                        ObjectOutputStream os = new ObjectOutputStream(readingclient.getOutputStream());
                        os.writeObject(intArrayList);
                        os.flush();
                        System.out.println("wrote data back success");
                        break;
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

}