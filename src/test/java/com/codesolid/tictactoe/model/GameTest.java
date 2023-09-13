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

        game.makeMove(1);
        game.makeMove(4);
        game.makeMove(7);

        // TODO: Test this
    }
}