package com.codesolid.tictactoe.model;

import java.util.Objects;
import java.util.stream.Stream;

public class Game {
    private final String[][] tiles;

    public Game() {
        this.tiles = new String[3][3];
    }

    public void makeMove(int index) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (i * 3 + j == index - 1) {
                    tiles[i][j] = "x";
                }
            }
        }
    }

    public String determinePlayersTurn() {
        long count = Stream.of(tiles).flatMap(Stream::of).filter(Objects::nonNull).count();
        return count % 2 == 0 ? "x" : "o";
    }

    public String[][] getTiles() {
        return tiles;
    }
}
