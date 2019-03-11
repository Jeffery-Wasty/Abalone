import java.util.ArrayList;

import javafx.animation.KeyFrame; 
import javafx.animation.Timeline; 
import javafx.application.Application; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.stage.Stage; 
import javafx.util.Duration; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox; 
import javafx.scene.paint.Color; 
import javafx.scene.text.Font; 
import javafx.scene.control.Button;

public class FXTimer extends Application 
{ 
    Timeline time; 
    KeyFrame frame;
    //used when time is kept running
    ArrayList<Double> moveTimes = new ArrayList<Double>();
    static final double DIVISOR = 1000.0;
    
    double startTime; 
    double endTime;
    double currentCounter = 0;
    double storeTimeWhenPaused = 0;
    boolean stopped = false;
    
    boolean blackMove = true;
    static int blackCol = 1;
    static int whiteCol = 2;
    int currentRow = 4; //todo dynamic
    
    Label lb; 
    Stage windows;  

    Button buttonStart;
    Button buttonPause;
    Button buttonStop;
    Button buttonReset;
    
    @Override public void start(Stage primaryStage) 
    { 
        windows= primaryStage; 
        Group root= new Group();
        GridPane layout = new GridPane();
        
        //The timer starts when the start button is pressed.
        //The pause button signifies the end of a turn, and calculates the time taken for that 'move'.
        //The next turn starts when the start button is pressed again.
        //The stop button functions like the pause.
        //The reset button can only be pressed AFTER the stop button is pressed, and once it is pressed the timer starts
        //counting again at 0.
        buttonStart = new Button("Start");
        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                doTime();
            }
        });
        
   
        buttonPause = new Button("Pause");
        buttonPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                time.stop();
                moveTimes.add(currentCounter);
                storeTimeWhenPaused += currentCounter;
                
                if (blackMove)
                    layout.add(new Label(currentCounter / DIVISOR + ""), blackCol, currentRow);
                else
                    layout.add(new Label(currentCounter / DIVISOR + ""), whiteCol, currentRow++);
                
                blackMove = !blackMove;
                currentCounter = 0;
                
            }
        });
        
        buttonStop = new Button("Stop");
        buttonStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                stopped = true;
                time.stop();
                storeTimeWhenPaused += currentCounter;
                
                if (blackMove)
                    layout.add(new Label(currentCounter / DIVISOR + ""), blackCol, currentRow);
                else
                    layout.add(new Label(currentCounter / DIVISOR + ""), whiteCol, currentRow++);
                
                blackMove = !blackMove;
                currentCounter = 0;
            }
        });
        
        buttonReset = new Button("Reset");
        buttonReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if (stopped) 
                {
                    currentCounter = 0;
                    storeTimeWhenPaused = 0;
                    stopped = false;
                    doTime();
                } 
            }
        });
        
        lb= new Label("Time: ");  
        
        Label blackMoveTimes = new Label("Black:");
        Label whiteMoveTimes = new Label("White:");
         
        layout.add(lb, 5, 1);
        layout.add(buttonStart, 1, 1);
        layout.add(buttonPause, 2, 1);
        layout.add(buttonStop, 3, 1);
        layout.add(buttonReset, 4, 1);
        layout.add(blackMoveTimes, 1, 3);
        layout.add(whiteMoveTimes, 2, 3);
        root.getChildren().add(layout); 
        Scene scene = new Scene(root, 500,500, Color.WHITE); 
        windows.setScene(scene); 
        windows.show(); 
    } 
    
    
    private void doTime() 
    {
        startTime = System.currentTimeMillis();
        
        time= new Timeline(); 
        frame = new KeyFrame(Duration.seconds(0.01), 
                new EventHandler<ActionEvent>()
                { 
                    @Override public void handle(ActionEvent event) 
                    {  
                        endTime = System.currentTimeMillis();
                        currentCounter = endTime - startTime;
                        lb.setText("Time: " + (storeTimeWhenPaused + currentCounter) / DIVISOR + " seconds");  
                    } 
                }); 
        time.setCycleCount(Timeline.INDEFINITE); 
        time.getKeyFrames().add(frame); 
        
        time.play(); 
        
    }
    
    public static void main(String[] args) { 
        launch(args); 
    } 
}


































