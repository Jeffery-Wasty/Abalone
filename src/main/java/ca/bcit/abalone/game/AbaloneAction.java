package ca.bcit.abalone.game;

public class AbaloneAction {

    public static final byte LEFT = 0;
    public static final byte UP_LEFT = 1;
    public static final byte UP_RIGHT = 2;
    public static final byte RIGHT = 3;
    public static final byte DOWN_RIGHT = 4;
    public static final byte DOWN_LEFT = 5;

    public final byte numberOfMarbles; // if this number = 1, it is a push action. if 1 = n <= 3. it's the number of marbles to move diagonally
    public final byte location; // the position of the left most marble
    public final byte direction; // the direction the marbles move to.

    public AbaloneAction(int numberOfMarbles, int location, int direction) {
        this.numberOfMarbles = (byte) numberOfMarbles;
        this.location = (byte) location;
        this.direction = (byte) direction;
    }

    @Override
    public String toString() {
        return "AbaloneAction{" +
                "numberOfMarbles=" + numberOfMarbles +
                ", location=" + location +
                ", direction=" + direction +
                "}\n";
    }
}