package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class Board extends JComponent
{
   // dimension of checkerboard square (25% bigger than checker)
   public final static int SQUAREDIM = (int) (Checker.getDimension() * 1.25);

   // dimension of checkerboard (width of 8 squares)
   private final int BOARDDIM = 8 * SQUAREDIM;

   // preferred size of Board component
   private Dimension dimPrefSize;

   // dragging flag -- set to true when user presses mouse button over checker
   // and cleared to false when user releases mouse button
   private boolean inDrag = false;

   // displacement between drag start coordinates and checker center coordinates
   private int deltax, deltay;

   // reference to positioned checker at start of drag
   public PosCheck posCheck;

   // center location of checker at start of drag
   public int oldcx, oldcy;

   // list of Checker objects and their initial positions
   public ArrayList<PosCheck> posChecks;
   public ArrayList<Integer> removedPieces;

   //check if this is the first checker move
   public boolean isFirstMoveOfChain;


   // store the location of the last move for "chain moves"
   public int lastmovex = -1;
   public int lastmovey = -1;
   public PosCheck lastposcheck;

   public boolean didTakeOver;

   public PosCheck chainPiece;



   // player of client
   int playernum = -1;

   public Board()
   {
      posChecks = new ArrayList<>();
      removedPieces = new ArrayList<>();
      dimPrefSize = new Dimension(BOARDDIM, BOARDDIM);

      addMouseListener(new MouseAdapter()
                       {
                          @Override
                          public void mousePressed(MouseEvent me)
                          {
                             // Obtain mouse coordinates at time of press.

                             int x = me.getX();
                             int y = me.getY();

                             // Locate positioned checker under mouse press.

                             for (PosCheck posCheck: posChecks)
                                if (Checker.contains(x, y, posCheck.cx, 
                                                     posCheck.cy))
                                {
                                   Board.this.posCheck = posCheck;
                                   oldcx = posCheck.cx;
                                   oldcy = posCheck.cy;
                                   deltax = x - posCheck.cx;
                                   deltay = y - posCheck.cy;
                                   inDrag = true;
                                   return;
                                }
                          }

                          @Override
                          public void mouseReleased(MouseEvent me)
                          {
                             // When mouse released, clear inDrag (to
                             // indicate no drag in progress) if inDrag is
                             // already set.

                             if (inDrag)
                                inDrag = false;
                             else
                                return;

                             // Snap checker to center of square.

                             int x = me.getX();
                             int y = me.getY();
                             posCheck.cx = (x - deltax) / SQUAREDIM * SQUAREDIM + 
                                           SQUAREDIM / 2;
                             posCheck.cy = (y - deltay) / SQUAREDIM * SQUAREDIM + 
                                           SQUAREDIM / 2;

                             // Do not move checker onto an occupied square.

                             for (PosCheck posCheck: posChecks)
                                if (posCheck != Board.this.posCheck && 
                                    posCheck.cx == Board.this.posCheck.cx &&
                                    posCheck.cy == Board.this.posCheck.cy)
                                {
                                   Board.this.posCheck.cx = oldcx;
                                   Board.this.posCheck.cy = oldcy;
                                }
                                int xToRemove = -1;
                                int yToRemove = -1;
                                boolean valid = false;
                                //Check for the valid move.
                                for (PosCheck posCheck: posChecks)

                                 if (Board.this.posCheck.checker.getType() == CheckerType.RED_REGULAR){



                                     if(getLocIndex(oldcy) - getLocIndex(Board.this.posCheck.cy) == 1 &&
                                               (getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == 1 ||
                                               getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == -1)) {

                                            valid = true;

                                     }else if (getLocIndex(oldcy) - getLocIndex(Board.this.posCheck.cy) == 2 &&
                                                  getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == 2) {
                                            if (getColorFromLocation((getLocIndex(oldcx) - 1), (getLocIndex(oldcy) - 1)) == CheckerColor.BLACK) {
                                                valid = true;
                                                xToRemove = getLocIndex(oldcx) - 1;
                                                yToRemove = getLocIndex(oldcy) - 1;
                                            }
                                     }else if (getLocIndex(oldcy) - getLocIndex(Board.this.posCheck.cy) == 2 &&
                                                  getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == -2) {
                                           if (getColorFromLocation((getLocIndex(oldcx) + 1), (getLocIndex(oldcy) - 1)) == CheckerColor.BLACK) {
                                              valid = true;
                                              xToRemove = getLocIndex(oldcx) + 1;
                                              yToRemove = getLocIndex(oldcy) - 1;
                                           }
                                     }

                                }else if(Board.this.posCheck.checker.getType() == CheckerType.RED_KING){
                                     if((getLocIndex(oldcy) - getLocIndex(Board.this.posCheck.cy) == -1 ||
                                             getLocIndex(oldcy) - getLocIndex(Board.this.posCheck.cy) == 1) &&(
                                             (getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == 1 ||
                                                     getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == -1))) {

                                         valid = true;
                                     }else if (getLocIndex(oldcy) - getLocIndex(Board.this.posCheck.cy) == -2 &&
                                             getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == 2) {
                                         if (getColorFromLocation((getLocIndex(oldcx) - 1), (getLocIndex(oldcy) + 1)) == CheckerColor.BLACK) {
                                             xToRemove = getLocIndex(oldcx) - 1;
                                             yToRemove = getLocIndex(oldcy) + 1;
                                             valid = true;
                                         }
                                     }else if (getLocIndex(oldcy) - getLocIndex(Board.this.posCheck.cy) == -2 &&
                                             getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == -2) {
                                         if (getColorFromLocation((getLocIndex(oldcx) + 1), (getLocIndex(oldcy) + 1)) == CheckerColor.BLACK) {
                                             xToRemove = getLocIndex(oldcx) + 1;
                                             yToRemove = getLocIndex(oldcy) + 1;
                                             valid = true;
                                         }
                                     }else if (getLocIndex(oldcy) - getLocIndex(Board.this.posCheck.cy) == 2 &&
                                             getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == 2) {
                                         if (getColorFromLocation((getLocIndex(oldcx) - 1), (getLocIndex(oldcy) - 1)) == CheckerColor.BLACK) {
                                             xToRemove = getLocIndex(oldcx) - 1;
                                             yToRemove = getLocIndex(oldcy) - 1;
                                             valid = true;
                                         }
                                     }else if (getLocIndex(oldcy) - getLocIndex(Board.this.posCheck.cy) == 2 &&
                                             getLocIndex(oldcx) - getLocIndex(Board.this.posCheck.cx) == -2) {
                                         if (getColorFromLocation((getLocIndex(oldcx) + 1), (getLocIndex(oldcy) - 1)) == CheckerColor.BLACK) {
                                             xToRemove = getLocIndex(oldcx) + 1;
                                             yToRemove = getLocIndex(oldcy) - 1;
                                             valid = true;
                                         }
                                     }

                                 // because the board is always facing the red player,
                                 // if they try to move the black pieces it should be invalid
                                }else if(Board.this.posCheck.checker.getType() == CheckerType.BLACK_REGULAR){
                                    valid=false;
                                }else if(Board.this.posCheck.checker.getType() == CheckerType.BLACK_KING){
                                    valid=false;
                                }

                                // check for a chain move, you can only move the same piece twice
                                if(lastmovex != -1 && lastmovey != -1) {
                                    if(Board.this.posCheck.cx != lastmovex || Board.this.posCheck.cy != lastmovey) {
                                        //valid=false; // they tried to move a different piece
                                        // reset the move graphically
                                        Board.this.posCheck.cx = oldcx;
                                        Board.this.posCheck.cy = oldcy;
                                        // reset the last move in memory to send over server correctly
                                        oldcx = lastmovex;
                                        oldcy = lastmovey;
                                        Board.this.posCheck = lastposcheck;
                                    }
                                }


                                if(Board.this.posCheck.checker.getType() == CheckerType.RED_REGULAR &&
                                        getLocIndex(Board.this.posCheck.cy) == 1){
                                 System.out.println("Upgrade");
                                    Board.this.posCheck.checker.setType(CheckerType.RED_KING);
                                 }
                              if(Board.this.posCheck.checker.getType() == CheckerType.BLACK_REGULAR &&
                                      getLocIndex(Board.this.posCheck.cy) == 8){
                                  System.out.println("Upgrade");
                                    Board.this.posCheck.checker.setType(CheckerType.BLACK_KING);
                              }

                              if(!isFirstMoveOfChain){
                                  if (Board.this.posCheck.cx != chainPiece.cx || Board.this.posCheck.cy != chainPiece.cy){
                                      System.out.println("This is not a chain piece");
                                      //valid = false;
                                  }
                              }

                                if (!valid){
                                    Board.this.posCheck.cx = oldcx;
                                    Board.this.posCheck.cy = oldcy;
                                    System.out.println("Invalid move");
                                } else {
                                    lastmovex = Board.this.posCheck.cx;
                                    lastmovey = Board.this.posCheck.cy;
                                    lastposcheck = Board.this.posCheck;
                                }

                                if (xToRemove != -1){
                                 remove(getCenterCoordinate(xToRemove), getCenterCoordinate(yToRemove));
                                 removedPieces.add(xToRemove);
                                 removedPieces.add(yToRemove);
                                }

                                isFirstMoveOfChain = false;
                                chainPiece = Board.this.posCheck;
                             repaint();
                          }
                       });

      // Attach a mouse motion listener to the applet. That listener listens
      // for mouse drag events.

      addMouseMotionListener(new MouseMotionAdapter()
                             {
                                @Override
                                public void mouseDragged(MouseEvent me)
                                {
                                   if (inDrag)
                                   {
                                      // Update location of checker center.

                                      posCheck.cx = me.getX() - deltax;
                                      posCheck.cy = me.getY() - deltay;
                                      repaint();
                                   }
                                }
                             });

       add(new Checker(CheckerType.RED_REGULAR), 8, 1);
       add(new Checker(CheckerType.RED_REGULAR), 6, 1);
       add(new Checker(CheckerType.RED_REGULAR), 8, 3);
       add(new Checker(CheckerType.RED_REGULAR), 6, 3);
       add(new Checker(CheckerType.RED_REGULAR), 8, 5);
       add(new Checker(CheckerType.RED_REGULAR), 6, 5);
       add(new Checker(CheckerType.RED_REGULAR), 8, 7);
       add(new Checker(CheckerType.RED_REGULAR), 6, 7);
       add(new Checker(CheckerType.RED_REGULAR), 7, 2);
       add(new Checker(CheckerType.RED_REGULAR), 7, 4);
       add(new Checker(CheckerType.RED_REGULAR), 7, 6);
       add(new Checker(CheckerType.RED_REGULAR), 7, 8);


       add(new Checker(CheckerType.BLACK_REGULAR), 1, 2);
       add(new Checker(CheckerType.BLACK_REGULAR), 3, 2);
       add(new Checker(CheckerType.BLACK_REGULAR), 1, 4);
       add(new Checker(CheckerType.BLACK_REGULAR), 3, 4);
       add(new Checker(CheckerType.BLACK_REGULAR), 1, 6);
       add(new Checker(CheckerType.BLACK_REGULAR), 3, 6);
       add(new Checker(CheckerType.BLACK_REGULAR), 1, 8);
       add(new Checker(CheckerType.BLACK_REGULAR), 3, 8);
       add(new Checker(CheckerType.BLACK_REGULAR), 2, 1);
       add(new Checker(CheckerType.BLACK_REGULAR), 2, 3);
       add(new Checker(CheckerType.BLACK_REGULAR), 2, 5);
       add(new Checker(CheckerType.BLACK_REGULAR), 2, 7);

   }


    public PosCheck getCheckerAt (int x, int y){
       for (PosCheck posCheck: posChecks)
           if (posCheck.cx == x && posCheck.cy == y) {
               return posCheck;
           }
        return new PosCheck();
   }

   public void remove(int xl, int yl){
       PosCheck pc = getCheckerAt(xl,yl);

       if (pc.checker != null) {
           System.out.println("poscheck remove debug"+getLocIndex(pc.cx)+","+getLocIndex(pc.cy));
           System.out.println("poscheck length (before delete):"+posChecks.size());
           posChecks.remove(pc);
           System.out.println("poscheck length (after delete):"+posChecks.size());
       }else{
           System.out.println("Unable to delete checker.");
       }
   }

   public void add(Checker checker, int row, int col)
   {
      if (row < 1 || row > 8)
         throw new IllegalArgumentException("row out of range: " + row);
      if (col < 1 || col > 8)
         throw new IllegalArgumentException("col out of range: " + col);
      PosCheck posCheck = new PosCheck();
      posCheck.checker = checker;
      posCheck.cx = (col - 1) * SQUAREDIM + SQUAREDIM / 2;
      posCheck.cy = (row - 1) * SQUAREDIM + SQUAREDIM / 2;
      for (PosCheck _posCheck: posChecks)
         if (posCheck.cx == _posCheck.cx && posCheck.cy == _posCheck.cy)
            throw new AlreadyOccupiedException("square at (" + row + "," +
                                               col + ") is occupied");
      posChecks.add(posCheck);
   }

   @Override
   public Dimension getPreferredSize()
   {
      return dimPrefSize;
   }

   @Override
   protected void paintComponent(Graphics g)
   {
      paintCheckerBoard(g);
      for (PosCheck posCheck: posChecks)
         if (posCheck != Board.this.posCheck)
            posCheck.checker.draw(g, posCheck.cx, posCheck.cy);

      // Draw dragged checker last so that it appears over any underlying 
      // checker.

      if (posCheck != null)
         posCheck.checker.draw(g, posCheck.cx, posCheck.cy);
   }

   private void paintCheckerBoard(Graphics g)
   {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

      // Paint checkerboard.

      for (int row = 0; row < 8; row++)
      {
         g.setColor(((row & 1) != 0) ? Color.BLACK : Color.WHITE);
         for (int col = 0; col < 8; col++)
         {
            g.fillRect(col * SQUAREDIM, row * SQUAREDIM, SQUAREDIM, SQUAREDIM);
            g.setColor((g.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK);
         }
      }
   }



   public void makeAMove(int x1, int y1, int x2, int y2){
       for (PosCheck posCheck: posChecks)
           if (posCheck.cx == getCenterCoordinate(9-x1) && posCheck.cy == getCenterCoordinate(9-y1)) {
               posCheck.cx = getCenterCoordinate(9-x2);
               posCheck.cy = getCenterCoordinate(9-y2);
               revalidate();
               repaint();
           }
   }


   public int getLocIndex(int loc){
      return (int) (loc/SQUAREDIM + 0.5) + 1;
   }

   public int getCenterCoordinate(int loc){
       return (loc-1)*SQUAREDIM + SQUAREDIM/2;
   }

   private CheckerColor getColorFromLocation(int xL, int yL){
       System.out.println(xL);
       System.out.println(yL);

       int x = getCenterCoordinate(xL);
       int y = getCenterCoordinate(yL);

      for (PosCheck posCheck: posChecks)
         if (posCheck.cx == x && posCheck.cy == y) {
            if (posCheck.checker.getType() == CheckerType.RED_KING || posCheck.checker.getType() == CheckerType.RED_REGULAR){
                System.out.println("RED");
                return CheckerColor.RED;
            }else if(posCheck.checker.getType() == CheckerType.BLACK_KING || posCheck.checker.getType() == CheckerType.BLACK_REGULAR){
                System.out.println("BLACK");
               return CheckerColor.BLACK;
            }
         }
       System.out.println("ERROR");
         return CheckerColor.ERROR;
   }
}