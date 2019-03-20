import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.animation.KeyFrame; 
import javafx.animation.Timeline;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import javafx.scene.Group; 
import javafx.scene.control.Label;
import javafx.scene.control.Button;

class FXTimer extends Group {
    private Timeline time;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<Double> moveTimes = new ArrayList<>();    // used when time is kept running
    private static final double DIVISOR = 1000.0;               // Divisor for time calculations
    private static final int TIME_SPACE_FACTOR = 20;            // padding between times
    private static final int BLACK_COL = 100;                   // y-axis location of black times
    private static final int WHITE_COL = 700;                   // y-axis location of white times

    private int currentRow = 50;                                // starting point for the calculated move times
    private boolean blackMove = true;                           // on white and black sides on white and black sides
    private int turn = 1;                                       // Player turn, each player increments the turn
    private int moveLimit = 0;                                  // Max number of 'stops' allowed (per player)
    private Label lb;                                           // Main time label
    
    //#region Time Calculation
    private double startTime; 
    private double endTime;
    private double currentCounter = 0;
    private double storeTimeWhenPaused = 0;
    private boolean stopped = true;
    private double timeLimit = 0;
    //#endregion

    //#region Time Buttons
    private Button buttonStart;
    private Button buttonPause;
    private Button buttonStop;
    private Button buttonReset;
    //#endregion

    FXTimer() {
        buildTimer();
    }
    
    /*

    Handles play for the button 'play'
    If you press play twice in a row, crash. Add bool to disallow pressing it twice.

    */
    private void doTime() {
        if (stopped && canStillMove()) {
            stopped = false;
            startTime = System.currentTimeMillis();

            time = new Timeline();
            KeyFrame frame = new KeyFrame(Duration.seconds(0.01),
                    event -> {

                        endTime = System.currentTimeMillis();
                        currentCounter = endTime - startTime;
                        if (timeLimit != 0 && (storeTimeWhenPaused + currentCounter) / DIVISOR >= timeLimit) {
                            stopped = true;
                            stop(true);
                            lb.setText("Time: " + new DecimalFormat("0.000").format(timeLimit) + " seconds");
                        } else {
                            lb.setText("Time: " + (storeTimeWhenPaused + currentCounter) / DIVISOR + " seconds");
                        }

                    });
            time.setCycleCount(Timeline.INDEFINITE);
            time.getKeyFrames().add(frame);

            time.play();
        }
    }
    
    /*

    handles the pause action for the buttons

    */
    private void stop(boolean maxed) {
        time.stop();
        moveTimes.add(maxed ? timeLimit * DIVISOR : currentCounter);
        Label l;
        int currentCol;
        if (blackMove) {
            currentCol = BLACK_COL;
            currentRow += TIME_SPACE_FACTOR;
        } else
            currentCol = WHITE_COL;
        
        l = new Label(new DecimalFormat("0.000").format(maxed ? timeLimit : currentCounter / DIVISOR) + "");
        l.setTranslateX(currentCol);
        l.setTranslateY(currentRow);
        getChildren().add(l);
        
        blackMove = !blackMove;
        currentCounter = 0;
        storeTimeWhenPaused = 0;
        ++turn;
    }

    private void pause() {
        time.stop();
        storeTimeWhenPaused += currentCounter;
    }

    private boolean canStillMove() {
        return (moveLimit == 0) || turn <= (moveLimit * 2);
    }

    private void initializeTooltips() {
        buttonPause.setTooltip(new Tooltip(
                "Pauses the timer, does not advance turns."));
        buttonReset.setTooltip(new Tooltip(
                "Resets the timer to zero."));
        buttonStart.setTooltip(new Tooltip(
                "Starts the timer from the currently displayed time."));
        buttonStop.setTooltip(new Tooltip(
                "Stops the timer and saves the time to the current user (black/white)."));
    }
   
    /*

    Builds the UI and button functionality for the FXTimer class.

    */
    private void buildTimer() {
        
        /*
        The timer starts when the start button is pressed.
        The pause button signifies the end of a turn, and calculates the time taken for that 'move'.
        The next turn starts when the start button is pressed again.
        The stop button functions like the pause.
        The reset button can only be pressed AFTER the stop button is pressed, and once it is pressed the timer starts
        counting again at 0.
        */
        buttonStart = new Button("Start");
        buttonStart.setTranslateX(300);
        buttonStart.setOnAction(e -> doTime());
        
        buttonPause = new Button("Pause");
        buttonPause.setTranslateX(400);
        buttonPause.setOnAction(e -> {
            if (!stopped && canStillMove()) {
                stopped = true;
                pause();
            }
        });
        
        buttonStop = new Button("Stop");
        buttonStop.setTranslateX(500);
        buttonStop.setOnAction(e -> {
            if (canStillMove()) {
                stopped = true;
                stop(false);
            }
        });
        
        buttonReset = new Button("Reset");
        buttonReset.setTranslateX(600);
        buttonReset.setOnAction(e -> {
            if (stopped && canStillMove()) {
                currentCounter = 0;
                storeTimeWhenPaused = 0;
                lb.setText("Time: " + (storeTimeWhenPaused + currentCounter) / DIVISOR + " seconds");
            }
        });

        initializeTooltips();

        lb = new Label("Time: " + (storeTimeWhenPaused + currentCounter) / DIVISOR + " seconds");
        lb.setTranslateX(100);
        
        Label blackMoveTimes = new Label("Black:");
        blackMoveTimes.setTranslateX(BLACK_COL);
        blackMoveTimes.setTranslateY(currentRow);
        Label whiteMoveTimes = new Label("White:");
        whiteMoveTimes.setTranslateX(WHITE_COL);
        whiteMoveTimes.setTranslateY(currentRow);

        getChildren().addAll(
                lb,
                buttonStart,
                buttonPause,
                buttonStop,
                buttonReset,
                blackMoveTimes,
                whiteMoveTimes
        );
    }

    @SuppressWarnings("unused")
    public double getTimeLimit() {
        return timeLimit;
    }

    void setTimeLimit(double timeLimit) {
        this.timeLimit = timeLimit;
    }

    @SuppressWarnings("unused")
    public int getMoveLimit() {
        return moveLimit;
    }

    void setMoveLimit(int moveLimit) {
        this.moveLimit = moveLimit;
    }
}


































