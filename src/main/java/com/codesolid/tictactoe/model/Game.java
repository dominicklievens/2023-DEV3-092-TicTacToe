package com.codesolid.tictactoe.model;

import org.apache.commons.lang3.StringUtils;

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
        // Expect number between 1 and 9
        // Arrays start at index 0, so subtract 1
        index--;
        int y = index / 3;
        int x = index % 3;

        tiles[y][x] = getCurrentPlayer();
        isGameFinished();
    }

    public boolean validMove(int index) {
        // Expect number between 1 and 9
        if (index < 1 || index > 9) {
            return false;
        }

        // Arrays start at index 0, so subtract 1
        index--;
        int y = index / 3;
        int x = index % 3;

        return isBlank(tiles[y][x]);
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
                gameState = "Winner is %s".formatted(tiles[i][0]);
                finished = true;
                return;
            }
            // Check columns
            if (tiles[0][i] != null && tiles[0][i].equals(tiles[1][i]) && tiles[0][i].equals(tiles[2][i])) {
                gameState = "Winner is %s".formatted(tiles[0][i]);
                finished = true;
                return;
            }
        }

        // Check diagonals
        if (tiles[0][0] != null && tiles[0][0].equals(tiles[1][1]) && tiles[0][0].equals(tiles[2][2])) {
            gameState = "Winner is %s".formatted(tiles[0][0]);
            finished = true;
            return;
        }
        if (tiles[0][2] != null && tiles[0][2].equals(tiles[1][1]) && tiles[0][2].equals(tiles[2][0])) {
            gameState = "Winner is %s".formatted(tiles[0][2]);
            finished = true;
            return;
        }

        // Check for a draw (no moves left)
        if (Stream.of(tiles).flatMap(Stream::of).noneMatch(StringUtils::isBlank)) {
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
