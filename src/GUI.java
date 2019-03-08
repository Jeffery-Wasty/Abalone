/**
 * 
 */
import java.util.HashMap;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * 
 * <p>This class plays with javafx and makes pretty shapes!.</p>
 * @author Sam
 * @version 1.0
 */
public class GUI extends Application {
    private HashMap<Integer, Circle> board = new HashMap<Integer, Circle>();
    /**
     * The dimensions of my scene.
     */
    static final int SCENEBARRIER = 500;
    
    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Group circlename = buildBoard();
 
        Scene myScene = new Scene(circlename, SCENEBARRIER, SCENEBARRIER);
        
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(myScene);
        primaryStage.show();

    }
    
    public Group buildBoard () {
      //Initiating board
        //Placing circles in hashmap similar to our tile objects.
        int rad = 20;
        int pos = 20;
        Group circlename = new Group();
        
        //ninth row
        // i = 9
        pos = 100;
        for (int i = 95; i < 100; i++)
        {
            Circle c = new Circle(pos+=40, 90, rad, Color.BROWN);
            board.put(i, c);
            circlename.getChildren().add(c);
        }
        
        //eighth row
        //h = 8
        pos = 80;
        for (int i = 84; i < 90; i++) {
            Circle c = new Circle(pos+=40, 130, rad, Color.BROWN);
            board.put(i, c);
            circlename.getChildren().add(c);
        }
            
        //seventh row
        //g = 7
        pos = 60;
        for (int i = 73; i < 80; i++) {
            Circle c = new Circle(pos+=40, 170, rad, Color.BROWN);
            board.put(i, c);
            circlename.getChildren().add(c);
        }
        
        //sixth row
        //g = 6
        pos = 40;
        for (int i = 62; i < 70; i++) {
            Circle c = new Circle(pos+=40, 210, rad, Color.BROWN);
            board.put(i, c);
            circlename.getChildren().add(c);
        
        }
        
        //fifth row
        pos = 20;
        for (int i = 51; i < 60; i++) {
            Circle c = new Circle(pos+=40, 250, rad, Color.BROWN);
            board.put(i, c);
            circlename.getChildren().add(c);
        }
        
        //fourth row
        pos = 40;
        for (int i = 41; i < 49; i++) {
            Circle c = new Circle(pos+=40, 290, rad, Color.BROWN);
            board.put(i, c);
            circlename.getChildren().add(c);
        }
        
        //third row
        pos = 60;
        for (int i = 31; i < 38; i++) {
            Circle c = new Circle(pos+=40, 330, rad, Color.BROWN);
            board.put(i, c);
            circlename.getChildren().add(c);
        }
        
        //second row
        pos = 80;
        for (int i = 21; i < 27; i++) {
            Circle c = new Circle(pos+=40, 370, rad, Color.BROWN);
            board.put(i, c);
            circlename.getChildren().add(c);
        }
        
        //first row
        pos = 100;
        for (int i = 11; i < 16; i++) {
            Circle c = new Circle(pos+=40, 410, rad, Color.BROWN);
            board.put(i, c);
            circlename.getChildren().add(c);
        }
        
        return circlename;
    }

    /**
     * Drives the program.
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }

}
