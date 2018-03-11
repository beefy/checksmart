package main;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Checkers extends JFrame
{
   public Checkers(String title)
   {
      super(title);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      Board board = new Board();
       board.add(new Checker(CheckerType.RED_REGULAR), 8, 1);
       board.add(new Checker(CheckerType.RED_REGULAR), 6, 1);
       board.add(new Checker(CheckerType.RED_REGULAR), 8, 3);
       board.add(new Checker(CheckerType.RED_REGULAR), 6, 3);
       board.add(new Checker(CheckerType.RED_REGULAR), 8, 5);
       board.add(new Checker(CheckerType.RED_REGULAR), 6, 5);
       board.add(new Checker(CheckerType.RED_REGULAR), 8, 7);
       board.add(new Checker(CheckerType.RED_REGULAR), 6, 7);
       board.add(new Checker(CheckerType.RED_REGULAR), 7, 2);
       board.add(new Checker(CheckerType.RED_REGULAR), 7, 4);
       board.add(new Checker(CheckerType.RED_REGULAR), 7, 6);
       board.add(new Checker(CheckerType.RED_REGULAR), 7, 8);
       
       
       board.add(new Checker(CheckerType.BLACK_REGULAR), 1, 2);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 3, 2);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 1, 4);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 3, 4);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 1, 6);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 3, 6);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 1, 8);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 3, 8);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 2, 1);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 2, 3);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 2, 5);
       board.add(new Checker(CheckerType.BLACK_REGULAR), 2, 7);
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
