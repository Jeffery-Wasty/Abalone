package ca.bcit.abalone;

import ca.bcit.abalone.game.AbaloneAction;
import ca.bcit.abalone.game.AbaloneGame;
import ca.bcit.abalone.game.Game;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AbaloneGameTest {

    AbaloneGame game = new AbaloneGame();

    @Before
    public void setUp() throws Exception {
        System.out.println(game);
    }

    @Test
    public void isValidAction() {
        // Push 0,0
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 0, 0, AbaloneAction.DOWN_LEFT))); // OO+
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 0, 0, AbaloneAction.DOWN_RIGHT))); // OOO+
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 0, 0, AbaloneAction.UP_LEFT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 0, 0, AbaloneAction.UP_RIGHT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 0, 0, AbaloneAction.LEFT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 0, 0, AbaloneAction.RIGHT))); //

        // Push 3,7
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 3, 7, AbaloneAction.DOWN_LEFT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 3, 7, AbaloneAction.DOWN_RIGHT))); //
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 3, 7, AbaloneAction.UP_LEFT))); // @@+
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 3, 7, AbaloneAction.UP_RIGHT))); // @@+
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 3, 7, AbaloneAction.LEFT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 3, 7, AbaloneAction.RIGHT))); //

        // Push 1,8
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.DOWN_LEFT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.DOWN_RIGHT))); //
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.UP_LEFT))); // @@+
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.UP_RIGHT))); // @@@+
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.LEFT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.RIGHT))); //

        // Push 2,8
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.DOWN_LEFT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.DOWN_RIGHT))); //
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.UP_LEFT))); // @@@+
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.UP_RIGHT))); // @@@+
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.LEFT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 1, 8, AbaloneAction.RIGHT))); //

        // Push 3,2
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 3, 2, AbaloneAction.DOWN_LEFT))); // O+
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 3, 2, AbaloneAction.DOWN_RIGHT))); // O+
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 3, 2, AbaloneAction.UP_LEFT))); //
        assertEquals(false, game.isValidAction(new AbaloneAction(1, 3, 2, AbaloneAction.UP_RIGHT))); //
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 3, 2, AbaloneAction.LEFT))); // OO+
        assertEquals(true, game.isValidAction(new AbaloneAction(1, 3, 2, AbaloneAction.RIGHT))); // OO+

        // side move 2,1,1
        assertEquals(true, game.isValidAction(new AbaloneAction(2, 1, 1, AbaloneAction.DOWN_LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 1, 1, AbaloneAction.DOWN_RIGHT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 1, 1, AbaloneAction.UP_LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 1, 1, AbaloneAction.UP_RIGHT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 1, 1, AbaloneAction.LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 1, 1, AbaloneAction.RIGHT)));

        // side move 3,1,1
        assertEquals(false, game.isValidAction(new AbaloneAction(3, 1, 1, AbaloneAction.DOWN_LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(3, 1, 1, AbaloneAction.DOWN_RIGHT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(3, 1, 1, AbaloneAction.UP_LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(3, 1, 1, AbaloneAction.UP_RIGHT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(3, 1, 1, AbaloneAction.LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(3, 1, 1, AbaloneAction.RIGHT)));

        assertEquals(true, game.isValidAction(new AbaloneAction(3, 4, 2, AbaloneAction.DOWN_LEFT)));
        assertEquals(true, game.isValidAction(new AbaloneAction(3, 4, 2, AbaloneAction.DOWN_RIGHT)));
        assertEquals(true, game.isValidAction(new AbaloneAction(2, 4, 1, AbaloneAction.DOWN_RIGHT)));

        assertEquals(false, game.isValidAction(new AbaloneAction(2, 0, 7, AbaloneAction.DOWN_LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 0, 7, AbaloneAction.DOWN_RIGHT)));
        assertEquals(true, game.isValidAction(new AbaloneAction(2, 0, 7, AbaloneAction.UP_LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 0, 7, AbaloneAction.UP_RIGHT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 0, 7, AbaloneAction.LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 0, 7, AbaloneAction.RIGHT)));

        assertEquals(false, game.isValidAction(new AbaloneAction(2, 5, 7, AbaloneAction.UP_LEFT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 5, 7, AbaloneAction.UP_RIGHT)));
        assertEquals(false, game.isValidAction(new AbaloneAction(2, 5, 7, AbaloneAction.RIGHT)));

        assertEquals(true, game.isValidAction(new AbaloneAction(2, 4, 1, AbaloneAction.DOWN_RIGHT)));
        assertEquals(true, game.isValidAction(new AbaloneAction(2, 1, 7, AbaloneAction.UP_LEFT)));
        // 2, 2, 2, 5
        assertEquals(true, game.isValidAction(new AbaloneAction(2, 2, 2, AbaloneAction.DOWN_LEFT)));

    }

    @Test
    public void execute() {
        Game<char[][], AbaloneAction> game = new AbaloneGame();

    }

    @Test
    public void copyState() {
        assertArrayEquals(AbaloneGame.INITIAL_STATE, game.makeStateCopy(AbaloneGame.INITIAL_STATE));
    }

    /**
     * initial state:
     * side moves = 10 per player
     * in-line moves:
     * outer row: 10 <= good here
     * middle row: 12 <= good here
     * inner row: 12 <= good here
     * total valid moves per player: 10 + 10 + 12 + 12 = 44
     */
    @Test
    public void actions() {
        System.out.println(Arrays.toString(game.validActions));
        assertEquals(88, game.validActions.length);
    }
}