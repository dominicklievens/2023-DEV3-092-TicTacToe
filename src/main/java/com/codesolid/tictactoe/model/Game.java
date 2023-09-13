package com.codesolid.tictactoe.model;

import java.util.Objects;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Game {
    private String[][] tiles;
    private String gameState;
    private boolean finished;

    public Game() {
        this.tiles = new String[3][3];
    }

    public void makeMove(int index) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (i * 3 + j == index - 1) {
                    tiles[i][j] = getCurrentPlayer();
                    isGameFinished();
                    return;
                }
            }
        }
    }

    public boolean validMove(int index) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (i * 3 + j == index - 1) {
                    return isBlank(tiles[i][j]);
                }
            }
        }
        return false;
    }

    public void reset() {
        tiles = new String[3][3];
        finished = false;
        gameState = null;
    }

    private void isGameFinished() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (tiles[i][0] != null && tiles[i][0].equals(tiles[i][1]) && tiles[i][0].equals(tiles[i][2])) {
                gameState = String.format("Winner is %s", tiles[i][0]);
                finished = true;
                return;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (tiles[0][j] != null && tiles[0][j].equals(tiles[1][j]) && tiles[0][j].equals(tiles[2][j])) {
                gameState = String.format("Winner is %s", tiles[0][j]);
                finished = true;
                return;
            }
        }
        // Check diagonals
        if (tiles[0][0] != null && tiles[0][0].equals(tiles[1][1]) && tiles[0][0].equals(tiles[2][2])) {
            gameState = String.format("Winner is %s", tiles[0][0]);
            finished = true;
            return;
        }
        if (tiles[0][2] != null && tiles[0][2].equals(tiles[1][1]) && tiles[0][2].equals(tiles[2][0])) {
            gameState = String.format("Winner is %s", tiles[0][2]);
            finished = true;
        }

        // Check for a draw (no moves left)
        boolean draw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isBlank(tiles[i][j])) {
                    draw = false;
                    break;
                }
            }
        }
        if (draw) {
            finished = true;
            gameState = "It's a draw!!";
        }
    }

    public String getCurrentPlayer() {
        long count = Stream.of(tiles).flatMap(Stream::of).filter(Objects::nonNull).count();
        return count % 2 == 0 ? "x" : "o";
    }

    public String[][] getTiles() {
        return tiles;
    }

    public String getGameState() {
        return gameState;
    }

    public boolean isFinished() {
        return finished;
    }
}
