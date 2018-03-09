package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Example extends JFrame {

    private final String WINDOW_TITLE = "Checksmart";
    private final int WINDOW_DIMEN = 400;
    private final int BUTTON_DIMEN = 40;

    private JButton quitButton;
    private JButton aboutButton;
    private JButton startButton;

    public final void init() {
        // Swing panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Centers buttons on resize
        panel.setBackground(Color.lightGray);
        setContentPane(panel);

        // Quit button setup
        quitButton = new JButton("Quit");
        quitButton.setToolTipText("Exit the program");
        quitButton.setBounds(BUTTON_DIMEN, BUTTON_DIMEN, BUTTON_DIMEN, BUTTON_DIMEN);
        quitButton.addActionListener(quit());
        panel.add(quitButton);

        aboutButton = new JButton("About");
        aboutButton.setToolTipText("Learn more about Checksmart");
        aboutButton.setBounds(BUTTON_DIMEN,BUTTON_DIMEN,BUTTON_DIMEN,BUTTON_DIMEN);
        aboutButton.addActionListener(about());
        panel.add(aboutButton);

        startButton = new JButton("Start");
        startButton.setToolTipText("Begin a game of checkers");
        startButton.setBounds(BUTTON_DIMEN,BUTTON_DIMEN,BUTTON_DIMEN,BUTTON_DIMEN);
        startButton.addActionListener(start());
        panel.add(startButton);

        setTitle(WINDOW_TITLE);
        setSize(WINDOW_DIMEN, WINDOW_DIMEN);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private ActionListener quit() {
        return e -> System.exit(0);
    }

    private ActionListener about() {
        return e -> {
            // TODO: open new screen to show about menu
            System.exit(0);
        };
    }

    private ActionListener start() {
        return e -> {
            // TODO: open new screen with game screen for user to play
            System.exit(0);
        };
    }
}
