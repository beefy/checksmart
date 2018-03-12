package main;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Checkers extends JFrame
{
   public Checkers(String title)
   {
      super(title);
      setSize(new Dimension(400,500));
      setBackground(Color.lightGray);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      // Set initial menu panel for JFrame
      setContentPane(new MainMenu());
      setVisible(true);
   }

   public static void main(String[] args)
   {
      Runnable r = () -> new Checkers("Checkers");
      EventQueue.invokeLater(r);
   }
}
