public class AbaloneAction {

    static final byte LEFT = 0;
    static final byte UP_LEFT = 1;
    static final byte UP_RIGHT = 2;
    static final byte RIGHT = 3;
    static final byte DOWN_RIGHT = 4;
    static final byte DOWN_LEFT = 5;

    final byte numberOfMarbles; // if this number = 1, it is a push action. if 1 = n <= 3. it's the number of marbles to move diagonally
    final byte location;        // the position of the left most marble
    final byte direction;       // the direction the marbles move to.

    AbaloneAction(int numberOfMarbles, int location, int direction) {
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