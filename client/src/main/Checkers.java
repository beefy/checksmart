package Main;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Checkers extends JFrame
{
   public Checkers(String title)
   {
      super(title);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      Board board = new Board();
      board.add(new Checker(CheckerType.BLACK_REGULAR), 4, 3);
      board.add(new Checker(CheckerType.RED_REGULAR), 5, 2);
      board.add(new Checker(CheckerType.RED_REGULAR), 5, 4);
      board.add(new Checker(CheckerType.RED_KING), 5, 6);
      board.add(new Checker(CheckerType.BLACK_KING), 6, 5);
      setContentPane(board);

      pack();
      setVisible(true);
   }

   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         new Checkers("Checkers");
                      }
                   };
      EventQueue.invokeLater(r);
   }
}