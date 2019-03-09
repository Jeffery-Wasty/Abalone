package ca.bcit.abalone;

public class AbaloneAction {

    public static final int LEFT = 0;
    public static final int UP_LEFT = 1;
    public static final int UP_RIGHT = 2;
    public static final int RIGHT = 3;
    public static final int DOWN_RIGHT = 4;
    public static final int DOWN_LEFT = 5;

    int numberOfMarbles; // if this number = 1, it is a push action. if 1 = n <= 3. it's the number of marbles to move diagonally
    int x; // the position of the left most marble
    int y; // ^^
    int direction; // the direction the marbles move to.

    public AbaloneAction(int numberOfMarbles, int x1, int y1, int direction) {
        this.numberOfMarbles = numberOfMarbles;
        this.x = x1;
        this.y = y1;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "AbaloneAction{" +
                "numberOfMarbles=" + numberOfMarbles +
                ", x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                "}\n";
    }
}