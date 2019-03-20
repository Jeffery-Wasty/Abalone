
import javafx.scene.paint.Color;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;



/**
 * 
 * <p>This class plays with javafx and makes pretty shapes!.</p>
 * @author Sam
 * @version 1.0
 */
class GameBoard extends Group {

    //Size of the board
    private static final int BOARD_SIZE = 61;
    
    //IF YOU WANT TO MOVE THE BOARD -- this will shift the entire board on the x-axis
    private static final int BOARD_START_POS_X = 370;
    
    //SHIFT THE WHOLE BOARD IN THE Y-AXIS
    private static final int BOARD_START_POS_Y = 110;
    
    private Piece[] board;
    private Button standardButton;
    private Button germanButton;
    private Button belgianButton;

    @SuppressWarnings("unused")
    private String modeChoice;
    @SuppressWarnings("unused")
    private String colorChoice;

    private TextField moveLimitInput;
    private int moveLimitValue;

    private TextField timeLimitInput;
    private int timeLimitValue;

    private AbaloneGame abaloneGame;
    
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<Integer> selectedPieces;
    //
    //builds the board
    //Is a group that is used in the GUI class
    //
    GameBoard() {
    	abaloneGame = new AbaloneGame();
    	selectedPieces = new ArrayList<>();
    	board = new Piece[BOARD_SIZE];
    	
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
    
    private void setLimit() {
        FXTimer timer = new FXTimer();

        Label moves = new Label("Move Limit: ");
    	moves.setTranslateY(650);
    	moves.setTranslateX(200);
    	
    	moveLimitInput = new TextField();
    	moveLimitInput.setTranslateY(650);
    	moveLimitInput.setTranslateX(300);

        Label time = new Label("Time");
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

    private void setColor() {
        Label color = new Label("Color: ");
    	color.setTranslateY(600);
    	color.setTranslateX(200);

        Button white = new Button("White");
    	white.setTranslateY(600);
    	white.setTranslateX(300);

        Button black = new Button("Black");
    	black.setTranslateY(600);
    	black.setTranslateX(400);
    	
    	getChildren().add(color);
    	getChildren().add(white);
    	getChildren().add(black);
    	white.setOnAction(event -> colorChoice = "white");
    	white.setOnAction(event -> colorChoice = "black");
    	
    }
    
    private void setGameMode() {
        Label mode = new Label("Mode: ");
    	mode.setTranslateY(550);
    	mode.setTranslateX(200);

        Button human = new Button("Human Vs Human");
    	human.setTranslateY(550);
    	human.setTranslateX(300);

        Button human_vs_CPU = new Button("Human Vs CPU");
    	human_vs_CPU.setTranslateY(550);
    	human_vs_CPU.setTranslateX(500);
    	
    	getChildren().add(mode);
    	getChildren().add(human);
    	getChildren().add(human_vs_CPU);
    	
    	human.setOnAction(event -> modeChoice = "human");
    	human.setOnAction(event -> modeChoice = "cpu");
    }
    
    private void setLayout(){
        Label layoutButtons = new Label("Layout: ");
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

    private void processMousePressed(MouseEvent event) {
        Object target = event.getTarget();
        Piece p = (Piece) target;
        
        selectedPieces.add(p.getPos());
        //variable = validation function()
        //if valid click
        p.setFill(Color.GREEN);
        //if not valid redraw board
        //if its a move, create the move object , redraw board
        
    } 
   
   private void buildBoard() {
       for (Piece piece : board) {
           getChildren().remove(piece);
       }
	   
	   int rad = 20;
       int posx = BOARD_START_POS_X;
       int posy = BOARD_START_POS_Y;
       int tileshift = posx;
       
       for (int i = 0; i < 5; i++)
       {
           tileshift = getTileShift(rad, posy, tileshift, i);
       }
       
       //second row
       // 5 - 10
       posx = posx - 20;
       posy = posy + 40;
       tileshift = posx;
       for (int i = 5; i < 11; i++) {
           tileshift = getTileShift(rad, posy, tileshift, i);
       }
       
       //third row
       // 11 - 17
       posx = posx - 20;
       posy = posy + 40;
       tileshift = posx;
       for (int i = 11; i < 18; i++) {
           tileshift = getTileShift(rad, posy, tileshift, i);
       }
       
       //fourth row
       // 18 - 25
       posx = posx - 20;
       posy = posy + 40;
       tileshift = posx;
       for (int i = 18; i < 26; i++) {
           tileshift = getTileShift(rad, posy, tileshift, i);

       }
       
       //fifth row
       // 26 - 34
       posx = posx - 20;
       posy = posy + 40;
       tileshift = posx;
       for (int i = 26; i < 35; i++) {
           tileshift = getTileShift(rad, posy, tileshift, i);
       }
       
       //sixth row
       //35 - 42
       posx = posx + 20;
       posy = posy + 40;
       tileshift = posx;
       for (int i = 35; i < 43; i++) {
           tileshift = getTileShift(rad, posy, tileshift, i);
       }
       
       //seventh row
       // 43 - 49
       posx = posx + 20;
       posy = posy + 40;
       tileshift = posx;
       for (int i = 43; i < 50; i++) {
           tileshift = getTileShift(rad, posy, tileshift, i);
       }
       
       //eighth row
       // 50 - 55
       posx = posx + 20;
       posy = posy + 40;
       tileshift = posx;
       for (int i = 50; i < 56; i++) {
           tileshift = getTileShift(rad, posy, tileshift, i);
       }
       
       //ninth row
       // 56 - 60
       posx = posx + 20;
       posy = posy + 40;
       tileshift = posx;
       for (int i = 56; i < 61; i++) {
           tileshift = getTileShift(rad, posy, tileshift, i);
       }
   }

    private int getTileShift(int rad, int posy, int tile_shift, int i) {
        Piece c = new Piece(tile_shift+=40, posy, rad, i);
        if(AbaloneGame.INITIAL_STATE[i] == 'O') {
            c.setFill(Color.WHITE);
        } else if(AbaloneGame.INITIAL_STATE[i] == '+') {
            c.setFill(Color.BROWN);
        } else {
            c.setFill(Color.BLACK);
        }
        board[i] = c;
        getChildren().add(c);
        return tile_shift;
    }
}