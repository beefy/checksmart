package main;
import java.awt.Color;
import java.awt.Graphics;

public final class Checker implements java.io.Serializable
{
   private final static int DIMENSION = 50;

   private CheckerType checkerType;

   public Checker(CheckerType checkerType)
   {
      this.checkerType = checkerType;
   }

   public void draw(Graphics g, int cx, int cy)
   {
      int x = cx - DIMENSION / 2;
      int y = cy - DIMENSION / 2;

      // Set checker color.

      g.setColor(checkerType == CheckerType.BLACK_REGULAR ||
                 checkerType == CheckerType.BLACK_KING ? Color.LIGHT_GRAY : 
                 Color.RED);

      // Paint checker.

//      g.fillOval(x, y, DIMENSION, DIMENSION);
//      g.setColor(Color.WHITE);
//      g.drawOval(x, y, DIMENSION, DIMENSION);
      if(checkerType == CheckerType.DELETED) {
         g.setColor(Color.BLACK);
         g.fillOval(x, y, DIMENSION, DIMENSION);
      } else {
         g.fillOval(x, y, DIMENSION, DIMENSION);
         g.setColor(Color.WHITE);
         g.drawOval(x, y, DIMENSION, DIMENSION);
      }



      if (checkerType == CheckerType.RED_KING || 
          checkerType == CheckerType.BLACK_KING)
         g.drawString("K", cx, cy);
   }

   public static boolean contains(int x, int y, int cx, int cy) {
      return (cx - x) * (cx - x) + (cy - y) * (cy - y) < DIMENSION / 2 * 
             DIMENSION / 2;
   }

   public static int getDimension()
   {
      return DIMENSION;
   }
   public CheckerType getType()
   {
      return checkerType;
   }
   public void setType(CheckerType type){checkerType = type;}
}
