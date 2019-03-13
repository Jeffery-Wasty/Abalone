/**
 * 
 */

import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;



/**
 * 
 * <p>This class plays with javafx and makes pretty shapes!.</p>
 * @author Sam
 * @version 1.0
 */
public class GameBoard extends Group {
    //
    // Unsure how we want handle clicks
    //
    private Piece firstClick;
    //
    private Piece secondClick;
    //
    private Piece thirdClick;
    
    //first click flag
    private boolean first = true;
    
    //Size of the pane
    static final int SCENEBARRIER = 1000;
    
    //Size of the board
    static final int BOARDSIZE = 61;
    
    //IF YOU WANT TO MOVE THE BOARD -- this will shift the entire board on the x-axis
    static final int BOARD_START_POSX = 370;
    
    //SHIFT THE WHOLE BOARD IN THE Y-AXIS
    static final int BOARD_START_POSY = 110;
    
    private Piece[] board;
    
    private Piece[] pieces;
    
    //
    //builds the board
    //Is a group that is used in the GUI class
    //
    public GameBoard() {
        board = new Piece[BOARDSIZE];
        pieces = new Piece[BOARDSIZE];
        buildBoard();
        standardLayout();
        setOnMousePressed(this::processMousePressed);
    }
    
    //
    //Unsure how we want to handle clicks and movement
    //
    public void processMousePressed(MouseEvent event) {
        Object target = event.getTarget();
        Piece p = (Piece) target;
       
        if (first && p.getFill() != Color.BROWN) {
            firstClick = p;
            first = !first;
            highlight();
        } else if (!first && p.getFill() == Color.BROWN) {
            secondClick = p;
            firstClick.setCenterX(secondClick.getCenterX());
            firstClick.setCenterY(secondClick.getCenterY());
            firstClick.setPos(secondClick.getPos()); //set the new location of the piece
            unhighlight();
            secondClick = null;
            firstClick = null;
            first = !first;
        }
    }
   
   //
   //Used to highlight the current tile selected
   //
   public void highlight() {
       firstClick.setStrokeWidth(5);
       firstClick.setStrokeType(StrokeType.OUTSIDE);
       firstClick.setStroke(Color.GREEN);
   }
   
   //
   //Unhighlight the selected piece after it has been moved.
   //
   public void unhighlight() {
       firstClick.setStrokeWidth(0);
   }
    
   //
   //Builds the board (The brown underlay for the pieces)
   //Creates Board Pieces and sets a position identical to the internal logic
   //
    public void buildBoard () {
        //Initiating board
        
        //first (top) row
        // 0 - 4
        int rad = 20;
        int posx = BOARD_START_POSX;
        int posy = BOARD_START_POSY;
        int tileshift = posx;
        for (int i = 0; i < 5; i++)
        {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BROWN);
            board[i] = c;
            getChildren().add(c);
        }
        
        //second row
        // 5 - 10
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 5; i < 11; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BROWN);
            board[i] = c;
            getChildren().add(c); 
        }
        
        //third row
        // 11 - 17
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 11; i < 18; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BROWN);
            board[i] = c;
            getChildren().add(c);
        }
        
        //fourth row
        // 18 - 25
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 18; i < 26; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BROWN);
            board[i] = c;
            getChildren().add(c);
        
        }
        
        //fifth row
        // 26 - 34
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 26; i < 35; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BROWN);
            board[i] = c;
            getChildren().add(c);
        }
        
        //sixth row
        //35 - 42
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 35; i < 43; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BROWN);
            board[i] = c;
            getChildren().add(c);
        }
        
        //seventh row
        // 43 - 49
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 43; i < 50; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BROWN);
            board[i] = c;
            getChildren().add(c);
        }
        
        //eighth row
        // 50 - 55
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 50; i < 56; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BROWN);
            board[i] = c;
            getChildren().add(c);
        }
        
        //ninth row
        // 56 - 60
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 56; i < 61; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BROWN);
            board[i] = c;
            getChildren().add(c);
        }
       
    }
    
    //
    //Builds a standard playout for the game pieces
    //
    public void standardLayout() {
        //Group standard = new Group();
        
        //white pieces
        //top row
        int rad = 15;
        int posx = BOARD_START_POSX;
        int posy = BOARD_START_POSY;
        int tileshift = posx;
        for (int i = 0; i < 5; i++)
        {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.WHITE);
            pieces[i] = c;
            getChildren().add(c);
        }
        
        //second row
        // 5 - 10
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 5; i < 11; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.WHITE);
            pieces[i] = c;
            getChildren().add(c);
        }
            
        //third row
        // 11 - 17
        posx = posx + 20; //starting drawing in the middle of the row
        posy = posy + 40;
        tileshift = posx + 40;
        for (int i = 13; i < 16; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.WHITE);
            pieces[i] = c;
            getChildren().add(c);
        }
        
        //black
        
        //seventh row
        // 43 - 49
        posy = posy + 160;
        tileshift = posx + 40;
        for (int i = 45; i < 48; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BLACK);
            pieces[i] = c;
            getChildren().add(c);
        }
        
        //eighth row
        // 50 - 55
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 50; i < 56; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BLACK);
            pieces[i] = c;
            getChildren().add(c);
        }
        
        //ninth row
        // 56 - 60
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 56; i < 61; i++) {
            Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BLACK);
            pieces[i] = c;
            getChildren().add(c);
        }
    }

  

}
