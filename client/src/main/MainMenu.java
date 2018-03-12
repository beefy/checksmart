package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {

    /**
     *  Main Menu JPanel holds JButtons for start, about, and quit buttons
     *  Components (Checkers game, about menu) added and removed at runtime
     *  as user interacts with the program
     */

    private JButton mainMenuButton;

    private JButton startButton;
    private JButton aboutButton;
    private JButton quitButton;

    private Board board;
    private JButton finishTurnButton;

    private JTextArea aboutText;

    public MainMenu() {
        init();
    }

    void init() {

        board = new Board();
        board.setVisible(false);
        add(board);

        setBackground(Color.lightGray);

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
        finishTurnButton.setVisible(false);
        add(finishTurnButton);

        aboutText = new JTextArea("Lorem ipsum dolor sit amet, consectetur adipisc\ning elit. " +
                "Aenean id nulla a ipsum accumsan congue nec sit\n amet urna. Ut id felis id diam " +
                "malesuada condimentum sagittis eu massa. In\nteger elementum commodo nisl laoreet volutpat." +
                " Ut tempus sem purus, in lacinia quam tempus eu. Etiam\n non mauris eu diam ornare dapibus " +
                "at eget libero. Suspendisse luctus dignissim nisi. Suspendisse \ndictum quam venenatis eros" +
                " sollicitudin, faucibus ultrices nunc\n gravida. Duis suscipit nibh arcu, sit amet venenatis" +
                " lectus aliquet et. Vivamus at pretium justo. \nVestibulum consectetur eros neque, in tempus" +
                " sem imperdiet sit amet.") {
            @Override
            public void setBorder(Border border) {
                // Don't set a border!
            }
        };
        aboutText.setBackground(Color.lightGray);
        aboutText.setFocusable(false);
        aboutText.setVisible(false);
        add(aboutText);

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(returnToMenu());
        mainMenuButton.setBounds(10,10,10,10);
        mainMenuButton.setLocation(0,10);
        mainMenuButton.setVisible(false);
        add(mainMenuButton);

        setVisible(true);
    }

    private ActionListener start() {
        return e -> {
            startButton.setVisible(false);
            aboutButton.setVisible(false);
            quitButton.setVisible(false);
            mainMenuButton.setVisible(true);
            board.setVisible(true);
            finishTurnButton.setVisible(true);
            mainMenuButton.setVisible(true);
            revalidate();
        };
    }

    private ActionListener about() {
        return e -> {
            startButton.setVisible(false);
            aboutButton.setVisible(false);
            quitButton.setVisible(false);
            board.setVisible(false);
            finishTurnButton.setVisible(false);

            mainMenuButton.setVisible(true);
            aboutText.setVisible(true);
            revalidate();
        };
    }

    private ActionListener quit() {
        return e -> System.exit(0);
    }

    private ActionListener makeMove() {
        return e -> {
            // TODO: Update server
        };
    }

    private ActionListener returnToMenu() {
        return e -> {
            startButton.setVisible(true);
            aboutButton.setVisible(true);
            quitButton.setVisible(true);

            mainMenuButton.setVisible(false);
            finishTurnButton.setVisible(false);

            board.setVisible(false);
            aboutText.setVisible(false);
        };
    }
}
