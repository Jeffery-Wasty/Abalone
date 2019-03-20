package ca.bcit.abalone;

import ca.bcit.abalone.game.AbaloneAction;
import ca.bcit.abalone.game.AbaloneGame;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AbaloneGameTest {

    @Test
    public void isValidAction() {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.STANDARD_INITIAL_STATE, 1), -1);
        // Push 0,0
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[0][0], AbaloneAction.DOWN_LEFT))); // OO+
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[0][0], AbaloneAction.DOWN_RIGHT))); // OOO+
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[0][0], AbaloneAction.UP_LEFT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[0][0], AbaloneAction.UP_RIGHT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[0][0], AbaloneAction.LEFT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[0][0], AbaloneAction.RIGHT))); //

        // Push 3,7
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[7][3], AbaloneAction.DOWN_LEFT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[7][3], AbaloneAction.DOWN_RIGHT))); //
        assertEquals(true, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[7][3], AbaloneAction.UP_LEFT))); // @@+
        assertEquals(true, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[7][3], AbaloneAction.UP_RIGHT))); // @@+
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[7][3], AbaloneAction.LEFT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[7][3], AbaloneAction.RIGHT))); //

        // Push 1,8
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.DOWN_LEFT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.DOWN_RIGHT))); //
        assertEquals(true, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.UP_LEFT))); // @@+
        assertEquals(true, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.UP_RIGHT))); // @@@+
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.LEFT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.RIGHT))); //

        // Push 2,8
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.DOWN_LEFT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.DOWN_RIGHT))); //
        assertEquals(true, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.UP_LEFT))); // @@@+
        assertEquals(true, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.UP_RIGHT))); // @@@+
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.LEFT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[8][1], AbaloneAction.RIGHT))); //

        // Push 3,2
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[2][3], AbaloneAction.DOWN_LEFT))); // O+
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[2][3], AbaloneAction.DOWN_RIGHT))); // O+
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[2][3], AbaloneAction.UP_LEFT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[2][3], AbaloneAction.UP_RIGHT))); //
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[2][3], AbaloneAction.LEFT))); // OO+
        assertEquals(false, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[2][3], AbaloneAction.RIGHT))); // OO+

        // side move 2,1,1
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.DOWN_LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.DOWN_RIGHT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.UP_LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.UP_RIGHT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.RIGHT)));

        // side move 3,1,1
        assertEquals(false, null != game.isValidAction(new AbaloneAction(3, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.DOWN_LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(3, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.DOWN_RIGHT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(3, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.UP_LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(3, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.UP_RIGHT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(3, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(3, AbaloneGame.LINEAR_LOCATION[1][1], AbaloneAction.RIGHT)));

        assertEquals(false, null != game.isValidAction(new AbaloneAction(3, AbaloneGame.LINEAR_LOCATION[2][4], AbaloneAction.DOWN_LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(3, AbaloneGame.LINEAR_LOCATION[2][4], AbaloneAction.DOWN_RIGHT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[1][4], AbaloneAction.DOWN_RIGHT)));

        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][0], AbaloneAction.DOWN_LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][0], AbaloneAction.DOWN_RIGHT)));
        assertEquals(true, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][0], AbaloneAction.UP_LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][0], AbaloneAction.UP_RIGHT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][0], AbaloneAction.LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][0], AbaloneAction.RIGHT)));

        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][5], AbaloneAction.UP_LEFT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][5], AbaloneAction.UP_RIGHT)));
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][5], AbaloneAction.RIGHT)));

        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[1][4], AbaloneAction.DOWN_RIGHT)));
        assertEquals(true, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[7][1], AbaloneAction.UP_LEFT)));
        // 2, 2, 2, 5
        assertEquals(false, null != game.isValidAction(new AbaloneAction(2, AbaloneGame.LINEAR_LOCATION[2][2], AbaloneAction.DOWN_LEFT)));

    }

    @Test
    public void execute() {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.STANDARD_INITIAL_STATE, 1), -1);
        System.out.println(game);
        game = game.result(game.isValidAction(new AbaloneAction(1, 56, 2)));
        System.out.println(game);
        game = game.result(game.isValidAction(new AbaloneAction(3, 15, 5)));
        System.out.println(game);
        game = game.result(game.isValidAction(new AbaloneAction(1, 53, 1)));
        System.out.println(game);
        game = game.result(game.isValidAction(new AbaloneAction(3, 9, 5)));
        System.out.println(game);
        game = game.result(game.isValidAction(new AbaloneAction(1, 46, 1)));
        System.out.println(game);
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
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.STANDARD_INITIAL_STATE, 1), -1);
        for (AbaloneGame.Action a : game.validActions) {
            System.out.println(a);
        }
        assertEquals(44, game.validActions.length);
    }

    @Test
    public void pushOpponentOutOfBoard() {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(new char[]{
                'O', 'O', 'O', 'O', 'O',
                'O', 'O', 'O', 'O', 'O', 'O',
                '@', '+', 'O', 'O', 'O', '+', '+',
                '@', '+', '+', '+', '+', '+', '+', '+',
                '@', '+', '+', '+', '+', '+', '+', '+', '+',
                '+', '+', '+', '+', '+', '+', '+', '+',
                '+', '+', '@', '@', '@', '+', '+',
                '@', '@', '@', '@', '@', '@',
                '@', '@', '@', '@', '@',
        }, 1), -1);
        assertEquals(true, null != game.isValidAction(new AbaloneAction(1, AbaloneGame.LINEAR_LOCATION[4][0], 2)));
    }

    @Test
    public void UIActionTest() {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.STANDARD_INITIAL_STATE, 1), -1);
//        assertArrayEquals(new int[]{1, 50, 2}, game.isValidUIMove(Arrays.asList(50, 44, 37)));
        assertArrayEquals(new int[]{3, 45, 1}, game.isValidUIMove(Arrays.asList(45, 46, 47, 39)));
    }
}