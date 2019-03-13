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

public class FXTimer extends Group 
{ 
    Timeline time; 
    KeyFrame frame;
    //used when time is kept running
    ArrayList<Double> moveTimes = new ArrayList<Double>();
    //Divisor for time calculations
    private static final double DIVISOR = 1000.0;
    //padding between times
    private static final int TIME_SPACE_FACTOR = 20;
    //y-axis location of black times
    private static final int BLACK_COL = 100;
    //y-axis location of white times
    private static final int WHITE_COL = 700;
    
    //starting point for the calculated move times
    private int currentRow = 50; 
    
    //variables used for time calculation
    private double startTime; 
    private double endTime;
    private double currentCounter = 0;
    private double storeTimeWhenPaused = 0;
    private boolean stopped = false;
    
    //used to toggle between displaying times
    //on white and black sides
    private boolean blackMove = true;

    //Main time label
    private Label lb;   

    //Self explanatory :)
    private Button buttonStart;
    private Button buttonPause;
    private Button buttonStop;
    private Button buttonReset;

    //Constructs a FXTimer that is a group, and can be displayed like a group
    public FXTimer() {
        buildTimer();
    }
    
    //
    //Handles play for the button 'play'
    //If you press play twice in a row, crash. Add bool to disallow pressing it twice.
    //
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
    
    //
    //handles the pause action for the buttons
    //
    public void pause() {
        time.stop();
        moveTimes.add(currentCounter);
        storeTimeWhenPaused += currentCounter;
        Label l;
        int currentCol;
        if (blackMove) {
            currentCol = BLACK_COL;
            currentRow+=TIME_SPACE_FACTOR;
        } else
            currentCol = WHITE_COL;
        
        l = new Label(currentCounter / DIVISOR + "");
        l.setTranslateX(currentCol);
        l.setTranslateY(currentRow);
        getChildren().add(l);
        
        blackMove = !blackMove;
        currentCounter = 0;
    }
   
    //
    //Builds the UI and button functionality for the FXTimer class.
    //
    public void buildTimer() {
        
        //The timer starts when the start button is pressed.
        //The pause button signifies the end of a turn, and calculates the time taken for that 'move'.
        //The next turn starts when the start button is pressed again.
        //The stop button functions like the pause.
        //The reset button can only be pressed AFTER the stop button is pressed, and once it is pressed the timer starts
        //counting again at 0.
        buttonStart = new Button("Start");
        buttonStart.setTranslateX(300);
        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                doTime();
            }
        });
        
        buttonPause = new Button("Pause");
        buttonPause.setTranslateX(400);
        buttonPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                pause();
            }
        });
        
        buttonStop = new Button("Stop");
        buttonStop.setTranslateX(500);
        buttonStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                stopped = true;
                pause();
            }
        });
        
        buttonReset = new Button("Reset");
        buttonReset.setTranslateX(600);
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
        
        lb = new Label("Time: "); 
        lb.setTranslateX(100);
        
        Label blackMoveTimes = new Label("Black:");
        blackMoveTimes.setTranslateX(BLACK_COL);
        blackMoveTimes.setTranslateY(currentRow);
        Label whiteMoveTimes = new Label("White:");
        whiteMoveTimes.setTranslateX(WHITE_COL);
        whiteMoveTimes.setTranslateY(currentRow);
         
        
        getChildren().add(lb);
        getChildren().add(buttonStart);
        getChildren().add(buttonPause);
        getChildren().add(buttonStop);
        getChildren().add(buttonReset);
        getChildren().add(blackMoveTimes);
        getChildren().add(whiteMoveTimes); 

    }
    
}


































