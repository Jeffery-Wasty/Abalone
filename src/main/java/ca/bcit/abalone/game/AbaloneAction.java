package ca.bcit.abalone.game;

public class AbaloneAction {

    public static final int LEFT = 0;
    public static final int UP_LEFT = 1;
    public static final int UP_RIGHT = 2;
    public static final int RIGHT = 3;
    public static final int DOWN_RIGHT = 4;
    public static final int DOWN_LEFT = 5;

    public final int numberOfMarbles; // if this number = 1, it is a push action. if 1 = n <= 3. it's the number of marbles to move diagonally
    public final int location; // the position of the left most marble
    public final int direction; // the direction the marbles move to.

    public AbaloneAction(int numberOfMarbles, int location, int direction) {
        this.numberOfMarbles = numberOfMarbles;
        this.location = location;
        this.direction = direction;
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