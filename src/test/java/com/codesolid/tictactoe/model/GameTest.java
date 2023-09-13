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
    void determinePlayersTurn() {
        var game = new Game();

        // First move is always X
        assertEquals("x", game.determinePlayersTurn());

        game.makeMove(1);
        assertEquals("o", game.determinePlayersTurn());

        game.makeMove(2);
        assertEquals("x", game.determinePlayersTurn());

        game.makeMove(3);
        assertEquals("o", game.determinePlayersTurn());

        game.makeMove(4);
        assertEquals("x", game.determinePlayersTurn());

        game.makeMove(5);
        assertEquals("o", game.determinePlayersTurn());
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