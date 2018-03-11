package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenu extends JPanel {

    /**
     *  Main Menu JPanel holds JButtons for start, about, and quit buttons
     *  Components (Checkers game, about menu) added and removed at runtime
     *  as user interacts with the program
     */

    private JButton start1Button;
    private JButton start2Button;
    private JButton aboutButton;
    private JButton quitButton;

    private Board board;
    private JButton finishTurnButton;

    private SendNetworkMessenger sendMsgr;
    private ReceiveNetworkMessenger receiveMsgr;

    public MainMenu() {
        init();
        sendMsgr = new SendNetworkMessenger();
        receiveMsgr = new ReceiveNetworkMessenger();
    }

    void init() {

        board = new Board();

        setBackground(Color.lightGray);

        start1Button = new JButton("Start Game (as player one)");
        start1Button.setToolTipText("Begin a game of checkers");
        start1Button.addActionListener(start1());
        start1Button.setBounds(10,10,10,10);
        start1Button.setLocation(0,10);
        add(start1Button);

        start2Button = new JButton("Start Game (as player two)");
        start2Button.setToolTipText("Begin a game of checkers");
        start2Button.addActionListener(start2());
        start2Button.setBounds(10,10,10,10);
        start2Button.setLocation(0,10);
        add(start2Button);

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

    private ActionListener start1() {
        return e -> {

            board.playernum = 1;

            remove(start1Button);
            remove(start2Button);
            remove(aboutButton);
            remove(quitButton);
            add(board);
            add(finishTurnButton);
            board.setVisible(true);
            revalidate();
        };
    }

    private ActionListener start2() {
        return e -> {

            board.playernum = 2;

            remove(start1Button);
            remove(start2Button);
            remove(aboutButton);
            remove(quitButton);
            add(board);
            add(finishTurnButton);
            board.setVisible(true);
            revalidate();

            sendMsgr.sendmessage(null);

            // receive next move and change board
            ArrayList<Integer> nextmove = receiveMsgr.receivemessage();
            int oldcx = nextmove.get(0);
            int oldcy = nextmove.get(1);
            int newcx = nextmove.get(2);
            int newcy = nextmove.get(3);

            System.out.println("in START2-MAINMENU.JAVA old:"+oldcx+","+oldcy);

            // move the piece to the new location
            PosCheck moving = board.getCheckerAt(oldcx, oldcy);
            moving.cx = newcx;
            moving.cy = newcy;
            board.remove(oldcy, oldcy);
            board.repaint();
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
