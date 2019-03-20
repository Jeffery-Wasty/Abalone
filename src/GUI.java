import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * 
 * GUI class for the abalone board and all of it's components
 * The GameBoard
 * The FXTimer
 * @author Sam
 * @version 1.1
 */
public class GUI extends Application {

    //Takes the GameBoard and FXTimer components (Which are groups) and adds them together.
    //Displays them both as one.
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        GameBoard abalone = new GameBoard();
        root.getChildren().add(abalone);

        int SCENE_BARRIER = 1000;
        Scene myScene = new Scene(root, SCENE_BARRIER, SCENE_BARRIER -150, Color.LIGHTGREY);
        
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
