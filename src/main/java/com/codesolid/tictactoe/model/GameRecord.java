package com.codesolid.tictactoe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_record")
public class GameRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "game_state")
    private String gameState;

    @Column(name = "move_count")
    private Integer moveCount;

    @Column(name = "date")
    private LocalDateTime date;

    public GameRecord() {
    }

    public GameRecord(String gameState, Integer moveCount, LocalDateTime date) {
        this.gameState = gameState;
        this.moveCount = moveCount;
        this.date = date;
    }

    public String getGameState() {
        return gameState;
    }

    public Integer getMoveCount() {
        return moveCount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
