package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

public class MainMenu extends JPanel {

    /**
     *  Main Menu JPanel holds JButtons for start, about, and quit buttons
     *  Components (Checkers game, about menu) added and removed at runtime
     *  as user interacts with the program
     */

    private JButton startButton;
    private JButton aboutButton;
    private JButton quitButton;

    private Board board;
    private JButton finishTurnButton;
    private JTextField playernumfield;

    private SendNetworkMessenger sendMsgr;
    private ReceiveNetworkMessenger receiveMsgr;

    final String IP_ADDRESS = "localhost";
    final int PORT = 1066;

    public MainMenu() {
        init();
        Socket socket = null;
        try {
            socket = new Socket(IP_ADDRESS, PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            // TODO: throw exception, without socket we can't play
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMsgr = new SendNetworkMessenger(socket);
        receiveMsgr = new ReceiveNetworkMessenger(socket);
    }

    void init() {

        board = new Board();

        setBackground(Color.lightGray);

        playernumfield = new JTextField("");
        add(playernumfield);

        startButton = new JButton("Start Game");
        startButton.setToolTipText("Begin a game of checkers");
        startButton.addActionListener(start());
        startButton.setBounds(10,10,10,10);
        startButton.setLocation(0,10);
        add(startButton);

        aboutButton = new JButton("About Game");
        aboutButton.setToolTipText("Learn more about ");
        aboutButton.addActionListener(about());
        aboutButton.setBounds(10,10,10,10);
        aboutButton.setLocation(0,10);
        add(aboutButton);

        quitButton = new JButton("Quit");
        quitButton.setToolTipText("Begin a game of checkers");
        quitButton.addActionListener(quit());
        quitButton.setBounds(10,10,10,10);
        quitButton.setLocation(0,10);
        add(quitButton);

        finishTurnButton = new JButton("Make Move");
        finishTurnButton.addActionListener(makeMove());
        finishTurnButton.setBounds(10,10,10,10);
        finishTurnButton.setLocation(0,10);

        setVisible(true);
    }

    private ActionListener start() {
        return e -> {

            remove(startButton);
            remove(aboutButton);
            remove(quitButton);
            add(board);
            add(finishTurnButton);
            board.setVisible(true);
            revalidate();

            // get player number from server
            board.playernum = receiveMsgr.receivemessage().get(0);
            playernumfield.setText("Player "+board.playernum);

            if(board.playernum == 2) {
                // receive first move and change board
                ArrayList<Integer> nextmove = receiveMsgr.receivemessage();
                int oldcx = nextmove.get(0);
                int oldcy = nextmove.get(1);
                int newcx = nextmove.get(2);
                int newcy = nextmove.get(3);

                System.out.println("in START-MAINMENU.JAVA [received first move]:" + oldcx + "," + oldcy);

                // move the piece to the new location
                PosCheck moving = board.getCheckerAt(oldcx, oldcy);
                moving.cx = newcx;
                moving.cy = newcy;
                board.remove(oldcy, oldcy);
                board.repaint();
            }
        };
    }

    private ActionListener about() {
        return e -> {
            // TODO: add about menu
            System.out.println("Test output: hit about button");
        };
    }

    private ActionListener quit() {
        return e -> System.exit(0);
    }

    private ActionListener makeMove() {
        return e -> {
            // build list and send it
            ArrayList<Integer> movelist = new ArrayList<Integer>();
            movelist.add(board.oldcx);
            movelist.add(board.oldcy);
            movelist.add(board.posCheck.cx);
            movelist.add(board.posCheck.cy);
            sendMsgr.sendmessage(movelist);


            System.out.println("in MAKEMOVE-MAINMENU.JAVA [sending]:"+board.oldcx+","+board.oldcy);

            // receive next move and change board
            ArrayList<Integer> nextmove = receiveMsgr.receivemessage();
            int oldcx = nextmove.get(0);
            int oldcy = nextmove.get(1);
            int newcx = nextmove.get(2);
            int newcy = nextmove.get(3);

            System.out.println("in MAKEMOVE-MAINMENU.JAVA [receiving]:"+oldcx+","+oldcy);

            // move the piece to the new location
            PosCheck moving = board.getCheckerAt(oldcx, oldcy);
            moving.cx = newcx;
            moving.cy = newcy;
            board.remove(oldcy, oldcy);
            board.repaint();
        };
    }
}
