

import javafx.scene.shape.Circle;

/**
 * @author Sam
 * The Piece class was created as so that whenever a circle is clicked on, we know what is position is. The position will
 * be used to communicate with the game logic for legal moves.
 *
 */
class Piece extends Circle {

    private int position;   //Giving the circles a 'position' so that they can link with the internal positioning logic

    Piece(int x, int y, int radius, int position) {
        super(x,y,radius);
        this.position = position;
    }
    
    int getPos() { return position;}
    
    //public void setPos(int pos) { position = pos;}
}
