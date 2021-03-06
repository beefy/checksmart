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

    // About button used only for the about menu
    private JButton aboutBackButton;
    private JTextArea aboutText;

    private Board board;
    private JButton finishTurnButton;
    private JButton resignButton;
    private JTextField playernumfield;

    private SendNetworkMessenger sendMsgr;
    private ReceiveNetworkMessenger receiveMsgr;

    final String IP_ADDRESS = "localhost";
    final int PORT = 1066;

    public MainMenu() {
        // draw the initial gui
        init();

        // initialize the socket
        Socket socket = null;
        try {
            socket = new Socket(IP_ADDRESS, PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            // TODO: throw exception, without socket we can't play
        } catch (IOException e) {
            e.printStackTrace();
        }
        // initialize the messengers with the socket
        sendMsgr = new SendNetworkMessenger(socket);
        receiveMsgr = new ReceiveNetworkMessenger(socket);

        // get player number from server
        System.out.println("waiting for player number...");
        receiveMsgr.receivemessage(new ReceiveNetworkMessenger.Callback() {
            @Override
            public void onSuccess(ArrayList<Integer> nextmove) {
                board.playernum = nextmove.get(0);
                playernumfield.setText("Player "+board.playernum);
            }
        });
    }

    void init() {

        board = new Board();

        setBackground(Color.lightGray);

        playernumfield = new JTextField("");
        add(playernumfield);

        // START MENU BUTTONS
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

        // PLAY GAME MENU BUTTONS
        finishTurnButton = new JButton("Make Move");
        finishTurnButton.addActionListener(makeMove());
        finishTurnButton.setBounds(10,10,10,10);
        finishTurnButton.setLocation(0,10);

        resignButton = new JButton("Resign");
        resignButton.addActionListener(resign());
        resignButton.setBounds(10,10,10,10);
        resignButton.setLocation(0,10);

        aboutBackButton = new JButton("Back");
        aboutBackButton.addActionListener(returnToMenu());
        aboutBackButton.setBounds(10,10,10,10);
        aboutBackButton.setLocation(0, 10);

        aboutText = new JTextArea("Checksmart is a Java based Checkers application\n" +
                "Version 1.0.0\n\n" +
                "\n" +
                "Copyright (c) 2018 Nate Schultz, Evan Radcliffe, Chingiz Mardanov, Steven Santos\n" +
                "\n" +
                "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                "of this software and associated documentation files (the \"Software\"), to deal\n" +
                "in the Software without restriction, including without limitation the rights\n" +
                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                "copies of the Software, and to permit persons to whom the Software is\n" +
                "furnished to do so, subject to the following conditions:\n" +
                "\n" +
                "The above copyright notice and this permission notice shall be included in all\n" +
                "copies or substantial portions of the Software.\n" +
                "\n" +
                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                "SOFTWARE.");

        setVisible(true);

        System.out.println("Menu GUI is drawn");
    }

    private ActionListener start() {
        return e -> {

            // REMOVE START MENU
            remove(startButton);
            remove(aboutButton);
            remove(quitButton);
            // ADD IN GAME MENU
            add(board);
            add(finishTurnButton);
            add(resignButton);
            board.setVisible(true);
            revalidate();

            if(board.playernum == 2) {
                // receive first move and change board
                receiveMsgr.receivemessage(new ReceiveNetworkMessenger.Callback() {
                    @Override
                    public void onSuccess(ArrayList<Integer> nextmove) {

                        int oldcx = nextmove.get(0);
                        int oldcy = nextmove.get(1);
                        int newcx = nextmove.get(2);
                        int newcy = nextmove.get(3);

                        System.out.println("in START-MAINMENU.JAVA [received first move]:" + oldcx + "," + oldcy);

                        // move the piece to the new location
                        board.makeAMove(board.getLocIndex(oldcx), board.getLocIndex(oldcy), board.getLocIndex(newcx), board.getLocIndex(newcy));
                    }
                });
            }
        };
    }

    private ActionListener about() {
        return e -> {
            // Add text and back button
            add(aboutBackButton);
            add(aboutText);

            // Remove everything else
            remove(board);
            remove(resignButton);
            remove(finishTurnButton);
            remove(quitButton);
            remove(startButton);
            remove(aboutButton);
            remove(playernumfield);

            revalidate();
            repaint();
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

            // add pieces to remove
            for(Integer i: board.removedPieces) {
                movelist.add(i);
            }
            board.removedPieces = new ArrayList<Integer>();

            sendMsgr.sendmessage(movelist);


            System.out.println("in MAKEMOVE-MAINMENU.JAVA [sending]:"+board.oldcx+","+board.oldcy);

            // receive next move and change board
            receiveMsgr.receivemessage(new ReceiveNetworkMessenger.Callback() {
                @Override
                public void onSuccess(ArrayList<Integer> nextmove) {

                    int oldcx = nextmove.get(0);
                    int oldcy = nextmove.get(1);
                    int newcx = nextmove.get(2);
                    int newcy = nextmove.get(3);

                    System.out.println("in MAKEMOVE-MAINMENU.JAVA [receiving]:"+oldcx+","+oldcy);



                    // remove pieces that were sent in addition to regular data
                    int counter = 0;
                    for(Integer i: nextmove) {
                        if(counter < 4) {
                            // do nothing
                        } else if(counter%2==0) {
                            // extra pieces to remove
                            System.out.println("removing after opponent took on "+nextmove.get(counter)+","+nextmove.get(counter+1));
                            int xtmp = board.getCenterCoordinate(9-nextmove.get(counter));
                            int ytmp = board.getCenterCoordinate(9-nextmove.get(counter+1));
                            board.getCheckerAt(xtmp,ytmp).checker.setType(CheckerType.DELETED);
                            board.remove(xtmp,ytmp);
                            revalidate();

                        }
                        counter += 1;
                    }
                    board.makeAMove(board.getLocIndex(oldcx), board.getLocIndex(oldcy), board.getLocIndex(newcx), board.getLocIndex(newcy));

                    // reset last piece move to reset possible chain
                    board.lastmovex = -1;
                    board.lastmovey = -1;
                    board.lastposcheck = null;
                }
            });
        };
    }

    private ActionListener resign() {
        return e -> {
            // REMOVE IN GAME MENU
            remove(board);
            remove(finishTurnButton);
            remove(resignButton);
            board.setVisible(false);
            // ADD START MENU
            add(startButton);
            add(aboutButton);
            add(quitButton);
            revalidate();
            repaint();
        };
    }

    private ActionListener returnToMenu() {
        return e -> {
            // Hide game information
            remove(board);
            remove(finishTurnButton);
            remove(resignButton);
            remove(aboutBackButton);
            remove(aboutText);
            board.setVisible(false);

            // Show main menu information
            add(startButton);
            add(aboutButton);
            add(quitButton);
            add(playernumfield);

            revalidate();
            repaint();
        };
    }
}
