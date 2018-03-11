import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkMessenger {

    final static String IP_ADDRESS = "localhost";
    final static int PORT = 1066;

    public static void sendmessage(String[] args) {
        try {
            Socket socket = new Socket(IP_ADDRESS, PORT);
            ObjectOutputStream obs = new ObjectOutputStream(socket.getOutputStream());
            ArrayList<Integer> initialList = new ArrayList<>();
            Integer inp;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type integer input: (-1 to stop)");
            while ((inp = scanner.nextInt()) != -1) {
                initialList.add(inp);
            }

            // Send list to server to be filtered
            obs.writeObject(initialList);

            // Read back updated list
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            try {
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
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ois.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}