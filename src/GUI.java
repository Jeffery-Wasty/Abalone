/**
 * 
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;




/**
 * 
 * <p>This class plays with javafx and makes pretty shapes!.</p>
 * @author Sam
 * @version 1.0
 */
public class GUI extends Application {
    //private HashMap<Integer, Circle> board = new HashMap<Integer, Circle>();
    private Circle[] board = new Circle[BOARDSIZE];
    private Circle[] pieces = new Circle[BOARDSIZE];
    
    private Circle firstClick;
    private Circle secondClick;
    private boolean first = true;
    /**
     * The dimensions of my scene.
     */
    static final int SCENEBARRIER = 1000;
    
    static final int BOARDSIZE = 61;
    
    //IF YOU WANT TO MOVE THE BOARD -- this will shift the entire board on the x-axis
    static final int BOARD_START_POSX = 370;
    
    //SHIFT THE WHOLE BOARD IN THE Y-AXIS
    static final int BOARD_START_POSY = 110;
    
    GameBoard abalone = new GameBoard();
    
    
    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        /*Group circlename = buildBoard();
        Group pieceLayout = standardLayout();

        circlename.getChildren().add(pieceLayout);*/
 
        Scene myScene = new Scene(abalone, SCENEBARRIER, SCENEBARRIER);
        
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(myScene);
        primaryStage.show();

    }
    
    /*
    private void setOnMousePressed(Object object) {
        //Object target = object.getTarget();
        if (first) {
            firstClick = (Circle) object;
            first = !first;
        } else {
            secondClick = (Circle) object;
            firstClick.setCenterX(secondClick.getCenterX());
            firstClick.setCenterY(secondClick.getCenterY());
        }
    }

   
    
    
   public void processMousePressed(MouseEvent event) {
       Object target = event.getTarget();
       if (first) {
           firstClick = (Circle) target;
           first = !first;
       } else {
           secondClick = (Circle) target;
           firstClick.setCenterX(secondClick.getCenterX());
           firstClick.setCenterY(secondClick.getCenterY());
       }
       
   }
    
    public Group buildBoard () {
      //Initiating board
        
        Group circlename = new Group();
        
        //first (top) row
        // 0 - 4
        int rad = 20;
        int posx = BOARD_START_POSX;
        int posy = BOARD_START_POSY;
        int tileshift = posx;
        for (int i = 0; i < 5; i++)
        {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BROWN);
            board[i] = c;
            circlename.getChildren().add(c);
        }
        
        //second row
        // 5 - 10
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 5; i < 11; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BROWN);
            board[i] = c;
            circlename.getChildren().add(c);
        }
            
        //third row
        // 11 - 17
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 11; i < 18; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BROWN);
            board[i] = c;
            circlename.getChildren().add(c);
        }
        
        //fourth row
        // 18 - 25
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 18; i < 26; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BROWN);
            board[i] = c;
            circlename.getChildren().add(c);
        
        }
        
        //fifth row
        // 26 - 34
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 26; i < 35; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BROWN);
            board[i] = c;
            circlename.getChildren().add(c);
        }
        
        //sixth row
        //35 - 42
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 35; i < 43; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BROWN);
            board[i] = c;
            circlename.getChildren().add(c);
        }
        
        //seventh row
        // 43 - 49
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 43; i < 50; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BROWN);
            board[i] = c;
            circlename.getChildren().add(c);
        }
        
        //eighth row
        // 50 - 55
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 50; i < 56; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BROWN);
            board[i] = c;
            circlename.getChildren().add(c);
        }
        
        //ninth row
        // 56 - 60
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 56; i < 61; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BROWN);
            board[i] = c;
            circlename.getChildren().add(c);
        }
        return circlename;
    }
    
    public Group standardLayout() {
        Group standard = new Group();
        
        //white pieces
        //top row
        int rad = 15;
        int posx = BOARD_START_POSX;
        int posy = BOARD_START_POSY;
        int tileshift = posx;
        for (int i = 0; i < 5; i++)
        {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.WHITE);
            pieces[i] = c;
            standard.getChildren().add(c);
        }
        
        //second row
        // 5 - 10
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 5; i < 11; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.WHITE);
            pieces[i] = c;
            standard.getChildren().add(c);
        }
            
        //third row
        // 11 - 17
        posx = posx + 20; //starting drawing in the middle of the row
        posy = posy + 40;
        tileshift = posx + 40;
        for (int i = 13; i < 16; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.WHITE);
            pieces[i] = c;
            standard.getChildren().add(c);
        }
        
        //black
        
        //seventh row
        // 43 - 49
        posy = posy + 160;
        tileshift = posx + 40;
        for (int i = 45; i < 48; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BLACK);
            pieces[i] = c;
            standard.getChildren().add(c);
        }
        
        //eighth row
        // 50 - 55
        posx = posx - 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 50; i < 56; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BLACK);
            pieces[i] = c;
            standard.getChildren().add(c);
        }
        
        //ninth row
        // 56 - 60
        posx = posx + 20;
        posy = posy + 40;
        tileshift = posx;
        for (int i = 56; i < 61; i++) {
            Circle c = new Circle(tileshift+=40, posy, rad, Color.BLACK);
            pieces[i] = c;
            standard.getChildren().add(c);
        }
        
        return standard;
    }
*/
    /**
     * Drives the program.
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }

}
