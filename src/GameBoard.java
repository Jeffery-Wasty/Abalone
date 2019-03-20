/**
 * 
 */

import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;



/**
 * 
 * <p>This class plays with javafx and makes pretty shapes!.</p>
 * @author Sam
 * @version 1.0
 */
public class GameBoard extends Group {
    
    //Size of the pane
    static final int SCENEBARRIER = 1000;
    
    //Size of the board
    static final int BOARDSIZE = 61;
    
    //IF YOU WANT TO MOVE THE BOARD -- this will shift the entire board on the x-axis
    static final int BOARD_START_POSX = 370;
    
    //SHIFT THE WHOLE BOARD IN THE Y-AXIS
    static final int BOARD_START_POSY = 110;
    
    private Piece[] board;
    private Label layoutButtons;
    private Button standardButton;
    private Button germanButton;
    private Button belgianButton;
    
    private Label mode;
    private Button human;
    private Button humanvsCPU;
    private String modeChoice;
    
    private Label color;
    private Button white;
    private Button black;
    private String colorChoice;
    
    private Label moves;
    private TextField moveLimitInput;
    private int moveLimitValue;
    
    private Label time;
    private TextField timeLimitInput;
    private int timeLimitValue;
    protected FXTimer timer;
    
    public AbaloneGame abaloneGame;
    
    protected ArrayList<Integer> selectedPieces;
    //
    //builds the board
    //Is a group that is used in the GUI class
    //
    public GameBoard() {
    	abaloneGame = new AbaloneGame();
    	selectedPieces = new ArrayList<>();
    	board = new Piece[BOARDSIZE];
    	
    	setLayout();
    	setGameMode(); 
    	setColor();
    	setLimit();
    	
        standardButton.setOnAction(event -> {
        	abaloneGame.standardLayout();
        	buildBoard();
        });
        
        germanButton.setOnAction(event -> {
        	abaloneGame.germanDaisy();
        	buildBoard();
        });
        
        belgianButton.setOnAction(event -> {
        	abaloneGame.belgianDaisy();
        	buildBoard();
        });
        
        setOnMousePressed(this::processMousePressed);
    }
    
    public void setLimit() {
    	timer = new FXTimer();
    	
    	moves = new Label("Move Limit: ");
    	moves.setTranslateY(650);
    	moves.setTranslateX(200);
    	
    	moveLimitInput = new TextField();
    	moveLimitInput.setTranslateY(650);
    	moveLimitInput.setTranslateX(300);
    	
    	time = new Label("Time");
    	time.setTranslateY(650);
    	time.setTranslateX(500);
    	
    	timeLimitInput = new TextField();
    	timeLimitInput.setTranslateY(650);
    	timeLimitInput.setTranslateX(600);
    	
    	getChildren().add(timer);
    	getChildren().add(moves);
    	getChildren().add(moveLimitInput);
    	
    	getChildren().add(time);
    	getChildren().add(timeLimitInput);
    	
    	moveLimitInput.setOnKeyPressed(e -> {
    		if(e.getCode().toString().equals( "ENTER")) {
    			moveLimitValue = Integer.valueOf(moveLimitInput.getText());
    			System.out.println(moveLimitValue);
    		}
    	});
    	timeLimitInput.setOnKeyPressed(e -> {
		if(e.getCode().toString().equals( "ENTER")) {
			timeLimitValue = Integer.valueOf(timeLimitInput.getText());
			System.out.println(timeLimitValue);
		}
    	});
    }

    public void setColor() {
    	color = new Label("Color: ");
    	color.setTranslateY(600);
    	color.setTranslateX(200);
    	
    	white = new Button("White");
    	white.setTranslateY(600);
    	white.setTranslateX(300);
    	
    	black = new Button("Black");
    	black.setTranslateY(600);
    	black.setTranslateX(400);
    	
    	getChildren().add(color);
    	getChildren().add(white);
    	getChildren().add(black);
    	white.setOnAction(event -> {
    		colorChoice = "white";
        });
    	
    	white.setOnAction(event -> {
    		colorChoice = "black";
        });
    	
    }
    
    public void setGameMode() {
    	mode = new Label("Mode: ");
    	mode.setTranslateY(550);
    	mode.setTranslateX(200);
    	
    	human = new Button("Human Vs Human");
    	human.setTranslateY(550);
    	human.setTranslateX(300);
    	
    	humanvsCPU = new Button("Human Vs CPU");
    	humanvsCPU.setTranslateY(550);
    	humanvsCPU.setTranslateX(500);
    	
    	getChildren().add(mode);
    	getChildren().add(human);
    	getChildren().add(humanvsCPU);
    	
    	human.setOnAction(event -> {
        	modeChoice = "human";
        });
    	human.setOnAction(event -> {
        	modeChoice = "cpu";
        });
    }
    
    public void setLayout(){
    	layoutButtons = new Label("Layout: "); 
    	layoutButtons.setTranslateY(500);
    	layoutButtons.setTranslateX(200);
    	
    	standardButton = new Button("Standard");
    	standardButton.setTranslateY(500);
    	standardButton.setTranslateX(300);
    	
    	germanButton = new Button("German Daisy");
    	germanButton.setTranslateY(500);
    	germanButton.setTranslateX(400);
    	
    	belgianButton = new Button("Belgian Daisy");
    	belgianButton.setTranslateY(500);
    	belgianButton.setTranslateX(530);
    	
    	getChildren().add(standardButton);
    	getChildren().add(germanButton);
    	getChildren().add(belgianButton);
    	getChildren().add(layoutButtons);
    }

    public void processMousePressed(MouseEvent event) {
        Object target = event.getTarget();
        Piece p = (Piece) target;
        
        selectedPieces.add(p.getPos());
        int[] moveResult = abaloneGame.isValidUIMove(selectedPieces);
        if(moveResult[0] == 0) {
        	p.setFill(Color.GREEN);
        } else if (moveResult[0] == -1) {
        	buildBoard();
        	selectedPieces.clear();
        } else {
        	AbaloneGame.Action action = abaloneGame.isValidAction(new AbaloneAction(moveResult[0], moveResult[1], moveResult[2]));
        	if(action != null) {
        		abaloneGame = abaloneGame.result(action);
            	buildBoard();
            	selectedPieces.clear();
        	}
        	
        }
    } 
   
   public void buildBoard() {
	   for(int i = 0; i < board.length; i ++) {
   			getChildren().remove(board[i]);
   		} 
	   
	   int rad = 20;
       int posx = BOARD_START_POSX;
       int posy = BOARD_START_POSY;
       int tileshift = posx;
       
       for (int i = 0; i < 5; i++)
       {
           Piece c = new Piece(tileshift+=40, posy, rad, i);
           if(AbaloneGame.INITIAL_STATE[i] == 'O') {
        	   c.setFill(Color.WHITE);
           } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
        	   c.setFill(Color.BROWN);
           } else {
        	   c.setFill(Color.BLACK);
           }
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
           if(AbaloneGame.INITIAL_STATE[i] == 'O') {
        	   c.setFill(Color.WHITE);
           } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
        	   c.setFill(Color.BROWN);
           } else {
        	   c.setFill(Color.BLACK);
           }
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
           if(AbaloneGame.INITIAL_STATE[i] == 'O') {
        	   c.setFill(Color.WHITE);
           } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
        	   c.setFill(Color.BROWN);
           } else {
        	   c.setFill(Color.BLACK);
           }
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
           if(AbaloneGame.INITIAL_STATE[i] == 'O') {
        	   c.setFill(Color.WHITE);
           } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
        	   c.setFill(Color.BROWN);
           } else {
        	   c.setFill(Color.BLACK);
           }
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
           if(AbaloneGame.INITIAL_STATE[i] == 'O') {
        	   c.setFill(Color.WHITE);
           } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
        	   c.setFill(Color.BROWN);
           } else {
        	   c.setFill(Color.BLACK);
           }
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
           if(AbaloneGame.INITIAL_STATE[i] == 'O') {
        	   c.setFill(Color.WHITE);
           } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
        	   c.setFill(Color.BROWN);
           } else {
        	   c.setFill(Color.BLACK);
           }
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
           if(AbaloneGame.INITIAL_STATE[i] == 'O') {
        	   c.setFill(Color.WHITE);
           } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
        	   c.setFill(Color.BROWN);
           } else {
        	   c.setFill(Color.BLACK);
           }
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
           if(AbaloneGame.INITIAL_STATE[i] == 'O') {
        	   c.setFill(Color.WHITE);
           } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
        	   c.setFill(Color.BROWN);
           } else {
        	   c.setFill(Color.BLACK);
           }
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
           if(AbaloneGame.INITIAL_STATE[i] == 'O') {
        	   c.setFill(Color.WHITE);
           } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
        	   c.setFill(Color.BROWN);
           } else {
        	   c.setFill(Color.BLACK);
           }
           board[i] = c;
           getChildren().add(c);
       }
   }
}