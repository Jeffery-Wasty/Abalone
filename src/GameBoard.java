/**
 * 
 */

import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
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
    
    private Label layoutButtons;
    private Button standardButton;
    private Button germanButton;
    private Button belgianButton;
    
    private Label mode;
    private Button human;
    private Button humanvsCPU;
    
    private Label color;
    private Button white;
    private Button black;
    
    private Label moves;
    private TextField moveLimitInput;
    
    private Label time;
    private TextField timeLimitInput;
    
    protected FXTimer timer;
    //
    //builds the board
    //Is a group that is used in the GUI class
    //
    public GameBoard() {
    	setLayout();
    	setGameMode(); 
    	setColor();
    	setLimit();
    	
    	
        board = new Piece[BOARDSIZE];
        pieces = new Piece[BOARDSIZE];
        buildBoard();
        
        standardButton.setOnAction(event -> {
        	buildBoard();
        	standardLayout();
        });
        
        germanButton.setOnAction(event -> {
        	buildBoard();
        	germanDaisy();
        });
        
        belgianButton.setOnAction(event -> {
        	buildBoard();
        	belgianDaisy();
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
            System.out.println(p.getPos());
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
    
    public void belgianDaisy() {
    	int rad = 15;
        int posx = BOARD_START_POSX;
        int posy = BOARD_START_POSY;
        int tileshift = posx ;
        
        //white r1
        for(int i = 0; i < 2 ; i++) {
        	 Piece c = new Piece(tileshift+=40, posy, rad, i);
             c.setFill(Color.WHITE);
             pieces[i] = c;
             getChildren().add(c);
        }
        
        //black r1
        tileshift += 40;
        for(int i = 3; i < 5 ; i++) {
       	 Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BLACK);
            pieces[i] = c;
            getChildren().add(c);
       }
        
        posx -=20;
        posy += 40;
        tileshift = posx;
        
        //white r2
        for(int i = 5; i < 8 ; i++) {
          	 Piece c = new Piece(tileshift+=40, posy, rad, i);
               c.setFill(Color.WHITE);
               pieces[i] = c;
               getChildren().add(c);
          }

        //blackr2
        for(int i = 8; i < 11 ; i++) {
         	 Piece c = new Piece(tileshift+=40, posy, rad, i);
              c.setFill(Color.BLACK);
              pieces[i] = c;
              getChildren().add(c);
         }
        
        posx -= 20;
        posy += 40;
        tileshift = posx + 40;
        //white r3
        for(int i = 12; i < 14 ; i++) {
         	 Piece c = new Piece(tileshift+=40, posy, rad, i);
              c.setFill(Color.WHITE);
              pieces[i] = c;
              getChildren().add(c);
         }
        
        //black r3
        tileshift += 40;
        for(int i = 15; i < 17 ; i++) {
        	 Piece c = new Piece(tileshift+=40, posy, rad, i);
             c.setFill(Color.BLACK);
             pieces[i] = c;
             getChildren().add(c);
        }
        
        //black r7
        tileshift = posx + 40;
        posy += 160;
        for(int i = 44; i < 46 ; i++) {
       	 Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BLACK);
            pieces[i] = c;
            getChildren().add(c);
       }
        
       //white r7
       tileshift += 40;
       for(int i = 47; i < 49 ; i++) {
         	 Piece c = new Piece(tileshift+=40, posy, rad, i);
              c.setFill(Color.WHITE);
              pieces[i] = c;
              getChildren().add(c);
         }
       
       posx += 20;
       posy += 40;
       tileshift = posx;
       
       //black r8
       for(int i = 50; i < 53 ; i++) {
         	 Piece c = new Piece(tileshift+=40, posy, rad, i);
              c.setFill(Color.BLACK);
              pieces[i] = c;
              getChildren().add(c);
         }
       
       //white r8
       for(int i = 53; i < 56 ; i++) {
       	 Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.WHITE);
            pieces[i] = c;
            getChildren().add(c);
       }
       
       posx += 20;
       posy += 40;
       tileshift = posx;
       
       //black r9
       for(int i = 56; i < 58 ; i++) {
       	 Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BLACK);
            pieces[i] = c;
            getChildren().add(c);
       }
       
       tileshift += 40;
       for(int i = 59; i < 61 ; i++) {
         	 Piece c = new Piece(tileshift+=40, posy, rad, i);
              c.setFill(Color.WHITE);
              pieces[i] = c;
              getChildren().add(c);
         }
       
    }
    
    public void germanDaisy() {
    	int rad = 15;
        int posx = BOARD_START_POSX;
        int posy = BOARD_START_POSY;
        int tileshift = posx - 20;
        posy = posy + 40;
        
        //white r2
        for(int i = 5; i < 7 ; i++) {
        	 Piece c = new Piece(tileshift+=40, posy, rad, i);
             c.setFill(Color.WHITE);
             pieces[i] = c;
             getChildren().add(c);
        }
        
        //black r2
        tileshift = posx + 140;
        for(int i = 9; i <11 ; i++) {
       	 Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BLACK);
            pieces[i] = c;
            getChildren().add(c);
       }
        
       //white r3
        posx -= 40;
        posy += 40 ;
        tileshift = posx;
        for(int i = 11; i <14 ; i++) {
          	 Piece c = new Piece(tileshift+=40, posy, rad, i);
               c.setFill(Color.WHITE);
               pieces[i] = c;
               getChildren().add(c);
          }
        
        //black r3
        tileshift += 40;
        for(int i = 15; i <18 ; i++) {
         	 Piece c = new Piece(tileshift+=40, posy, rad, i);
              c.setFill(Color.BLACK);
              pieces[i] = c;
              getChildren().add(c);
         }
        
        //white r4
        posx -= 40;
        posy += 40;
        tileshift = posx + 60;
        for(int i = 19; i <21 ; i++) {
        	 Piece c = new Piece(tileshift+=40, posy, rad, i);
             c.setFill(Color.WHITE);
             pieces[i] = c;
             getChildren().add(c);
        }
        
        //black r4
        tileshift += 80;
        for(int i = 23; i <25 ; i++) {
       	 Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.BLACK);
            pieces[i] = c;
            getChildren().add(c);
       }
        
       //black r6
       posy += 80;
       tileshift = posx + 60;
       for(int i = 36; i <38 ; i++) {
         	 Piece c = new Piece(tileshift+=40, posy, rad, i);
              c.setFill(Color.BLACK);
              pieces[i] = c;
              getChildren().add(c);
         }
       
       //white r6
       tileshift += 80;
       for(int i = 40; i <42 ; i++) {
       	 Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.WHITE);
            pieces[i] = c;
            getChildren().add(c);
       }
       
       //black r7
       posx += 40;
       posy += 40;
       tileshift = posx;
       for(int i = 43; i <46 ; i++) {
     	 Piece c = new Piece(tileshift+=40, posy, rad, i);
          c.setFill(Color.BLACK);
          pieces[i] = c;
          getChildren().add(c);
       }
       
       //white r7
       tileshift += 40;
       for(int i = 47; i <50 ; i++) {
       	 Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.WHITE);
            pieces[i] = c;
            getChildren().add(c);
         }
       
       //black r8
       posx += 20;
       posy += 40;
       tileshift = posx;
       for(int i = 50; i <52 ; i++) {
         	 Piece c = new Piece(tileshift+=40, posy, rad, i);
              c.setFill(Color.BLACK);
              pieces[i] = c;
              getChildren().add(c);
           }
       
       //white r8
       tileshift += 80;
       for(int i = 54; i <56 ; i++) {
       	 Piece c = new Piece(tileshift+=40, posy, rad, i);
            c.setFill(Color.WHITE);
            pieces[i] = c;
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
