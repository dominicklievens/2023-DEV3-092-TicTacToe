package com.codesolid.tictactoe.controller;

import com.codesolid.tictactoe.exceptions.InvalidMoveException;
import com.codesolid.tictactoe.model.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    @GetMapping("/state")
    public ResponseEntity<Game> getGame() {
        return ResponseEntity.ok(game);
    }

    @PostMapping("/make-move")
    public ResponseEntity<Game> makeMove(@RequestBody Integer tileNumber) {
        if (!game.validMove(tileNumber)) {
            throw new InvalidMoveException(String.format("This tileNumber is invalid: %s", tileNumber));
        }

        game.makeMove(tileNumber);
        return ResponseEntity.ok(game);
    }

    @ExceptionHandler(InvalidMoveException.class)
    public ResponseEntity<Map<String, String>> handleInvalidMoveException(InvalidMoveException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
