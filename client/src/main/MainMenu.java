package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

    public MainMenu() {
        init();
    }

    void init() {

        board = new Board();

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

        setVisible(true);
    }

    private ActionListener start() {
        return e -> {
            remove(startButton);
            add(board);
            board.setVisible(true);
            revalidate();
        };
    }

    private ActionListener about() {
        return e -> {
            // TODO: add about menu
        };
    }

    private ActionListener quit() {
        return e -> System.exit(0);
    }
}
