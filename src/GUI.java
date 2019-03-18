/**
 * 
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;


/**
 * 
 * GUI class for the abalone board and all of it's components
 * The GameBoard
 * The FXTimer
 * @author Sam
 * @version 1.0
 */
public class GUI extends Application {

    /**
     * The dimensions of my scene.
     */
    static final int SCENEBARRIER = 1000;

    //Takes the GameBoard and FXTimer components (Which are groups) and adds them together.
    //Displays them both as one.
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameBoard abalone = new GameBoard();
        //removed timer and put it into gameboard
//        abalone.getChildren().add(timer);
        Scene myScene = new Scene(abalone, SCENEBARRIER, SCENEBARRIER);
        
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(myScene);
        primaryStage.show();

    }
    
    
    /**
     * Drives the program.
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }

}
