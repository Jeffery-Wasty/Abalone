import java.util.HashMap;

import javafx.scene.shape.Circle;

/**
 * @author Sam
 *
 */
public class AbaloneBoard {

    private HashMap<Integer, Tile> board;
    
    public AbaloneBoard (HashMap<Integer, Tile> board) { this.board = board;}
    
    public void setStandardFormation(HashMap<Integer, Tile> baseboard) {
        //first row
        baseboard.put(11, new Tile(TileContent.white));
        baseboard.put(12, new Tile(TileContent.white));
        baseboard.put(13, new Tile(TileContent.white));
        baseboard.put(14, new Tile(TileContent.white));
        baseboard.put(15, new Tile(TileContent.white));
        
        //second
        baseboard.put(21, new Tile(TileContent.white));
        baseboard.put(22, new Tile(TileContent.white));
        baseboard.put(23, new Tile(TileContent.white));
        baseboard.put(24, new Tile(TileContent.white));
        baseboard.put(25, new Tile(TileContent.white));
        baseboard.put(26, new Tile(TileContent.white));
        
        //third
        baseboard.put(31, new Tile(TileContent.empty));
        baseboard.put(32, new Tile(TileContent.white));
        baseboard.put(33, new Tile(TileContent.white));
        baseboard.put(34, new Tile(TileContent.white));
        baseboard.put(35, new Tile(TileContent.white));
        baseboard.put(36, new Tile(TileContent.white));
        baseboard.put(37, new Tile(TileContent.empty));
        
        //fourth
        baseboard.put(41, new Tile(TileContent.empty));
        baseboard.put(42, new Tile(TileContent.empty));
        baseboard.put(43, new Tile(TileContent.empty));
        baseboard.put(44, new Tile(TileContent.empty));
        baseboard.put(45, new Tile(TileContent.empty));
        baseboard.put(46, new Tile(TileContent.empty));
        baseboard.put(47, new Tile(TileContent.empty));
        baseboard.put(48, new Tile(TileContent.empty));
        //fifth
        baseboard.put(51, new Tile(TileContent.empty));
        baseboard.put(52, new Tile(TileContent.empty));
        baseboard.put(53, new Tile(TileContent.empty));
        baseboard.put(54, new Tile(TileContent.empty));
        baseboard.put(55, new Tile(TileContent.empty));
        baseboard.put(56, new Tile(TileContent.empty));
        baseboard.put(57, new Tile(TileContent.empty));
        baseboard.put(58, new Tile(TileContent.empty));
        baseboard.put(59, new Tile(TileContent.empty));
        //sixth
        baseboard.put(62, new Tile(TileContent.empty));
        baseboard.put(63, new Tile(TileContent.empty));
        baseboard.put(64, new Tile(TileContent.empty));
        baseboard.put(65, new Tile(TileContent.empty));
        baseboard.put(66, new Tile(TileContent.empty));
        baseboard.put(67, new Tile(TileContent.empty));
        baseboard.put(68, new Tile(TileContent.empty));
        baseboard.put(69, new Tile(TileContent.empty));
        //seventh
        baseboard.put(73, new Tile(TileContent.empty));
        baseboard.put(74, new Tile(TileContent.black));
        baseboard.put(75, new Tile(TileContent.black));
        baseboard.put(76, new Tile(TileContent.black));
        baseboard.put(77, new Tile(TileContent.black));
        baseboard.put(78, new Tile(TileContent.black));
        baseboard.put(79, new Tile(TileContent.empty));
        //Eighth
        baseboard.put(84, new Tile(TileContent.black));
        baseboard.put(85, new Tile(TileContent.black));
        baseboard.put(86, new Tile(TileContent.black));
        baseboard.put(87, new Tile(TileContent.black));
        baseboard.put(88, new Tile(TileContent.black));
        baseboard.put(89, new Tile(TileContent.black));
        
        //ninth
        baseboard.put(95, new Tile(TileContent.black));
        baseboard.put(96, new Tile(TileContent.black));
        baseboard.put(97, new Tile(TileContent.black));
        baseboard.put(98, new Tile(TileContent.black));
        baseboard.put(99, new Tile(TileContent.black));
    }
    
    public void setGermanDaisy() {
        
    }
    
    public void setBelgianDaisy() {
        
    }
    

}
