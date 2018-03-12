
package main;
import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JFrame;

public class Checkers extends JFrame
{
   public Checkers(String title)
   {
      super(title);
      setSize(new Dimension(Board.SQUAREDIM*10,Board.SQUAREDIM*9));
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
