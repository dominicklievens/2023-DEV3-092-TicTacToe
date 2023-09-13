package com.codesolid.tictactoe.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void makeMove() {
        var game = new Game();

        game.makeMove(1);
        game.makeMove(4);
        game.makeMove(6);
        game.makeMove(9);

        assertNotNull(game.getTiles()[0][0]);
        assertNotNull(game.getTiles()[1][0]);
        assertNotNull(game.getTiles()[1][2]);
        assertNotNull(game.getTiles()[2][2]);

        assertNull(game.getTiles()[0][1]);
        assertNull(game.getTiles()[0][2]);
        assertNull(game.getTiles()[1][1]);
        assertNull(game.getTiles()[2][0]);
        assertNull(game.getTiles()[2][1]);
    }

    @Test
    void validMove() {
        var game = new Game();

        // Every move between from 1 to 9 should be valid in a new game
        for (int i = 1; i < 10; i++) {
            assertTrue(game.validMove(i));
        }

        // If tile is already filled, the move is not valid
        game.makeMove(1);
        assertFalse(game.validMove(1));
    }

    @Test
    void determinePlayersTurn() {
        var game = new Game();

        // First move is always X
        assertEquals("x", game.getCurrentPlayer());

        game.makeMove(1);
        assertEquals("o", game.getCurrentPlayer());

        game.makeMove(2);
        assertEquals("x", game.getCurrentPlayer());

        game.makeMove(3);
        assertEquals("o", game.getCurrentPlayer());

        game.makeMove(4);
        assertEquals("x", game.getCurrentPlayer());

        game.makeMove(5);
        assertEquals("o", game.getCurrentPlayer());
    }

    @Test
    void isWinningMove() {
        var game = new Game();

        //    x | o | o
        //    ---------
        //    x |   |
        //    ---------
        //    x |   |
        game.makeMove(1); // x
        game.makeMove(2); // o
        game.makeMove(4); // x
        game.makeMove(3); // o
        game.makeMove(7); // x
        assertTrue(game.isFinished());
        assertEquals("Winner is x", game.getGameState());

        game.reset();

        //    x | x | x
        //    ---------
        //    o |   |
        //    ---------
        //    o |   |
        game.makeMove(1); // x
        game.makeMove(4); // o
        game.makeMove(2); // x
        game.makeMove(7); // o
        game.makeMove(3); // x
        assertTrue(game.isFinished());
        assertEquals("Winner is x", game.getGameState());

        game.reset();

        // Column with x o x would not finish the game
        //    x | o | x
        //    ---------
        //      |   |
        //    ---------
        //      |   |
        game.makeMove(1); // x
        game.makeMove(4); // o
        game.makeMove(7); // x
        assertFalse(game.isFinished());
        assertNull(game.getGameState());

        game.reset();

        //    x | x | o
        //    ---------
        //    o | x |
        //    ---------
        //    o | o | x
        game.makeMove(1); // x
        game.makeMove(4); // o
        game.makeMove(2); // x
        game.makeMove(7); // o
        game.makeMove(5); // x
        game.makeMove(8); // o
        game.makeMove(9); // x
        assertTrue(game.isFinished());
        assertEquals("Winner is x", game.getGameState());

        game.reset();

        //    x | x | o
        //    ---------
        //    x | o |
        //    ---------
        //    o | o | x
        game.makeMove(1); // x
        game.makeMove(3); // o
        game.makeMove(2); // x
        game.makeMove(7); // o
        game.makeMove(4); // x
        game.makeMove(5); // o
        assertTrue(game.isFinished());
        assertEquals("Winner is o", game.getGameState());
    }

    //    o | x | x
    //    ---------
    //    x | o | o
    //    ---------
    //    o | x | x
    @Test
    void verifyDraw() {
        var game = new Game();
        game.makeMove(8); // x
        game.makeMove(6); // o
        game.makeMove(2); // x
        game.makeMove(1); // o
        game.makeMove(4); // x
        game.makeMove(5); // o
        game.makeMove(3); // x
        game.makeMove(7); // o
        game.makeMove(9); // x
        assertTrue(game.isFinished());
        assertEquals("It's a draw!!", game.getGameState());
    }
}