package com.codesolid.tictactoe.controller;

import com.codesolid.tictactoe.exceptions.InvalidMoveException;
import com.codesolid.tictactoe.model.Game;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    @Operation(summary = "Get game state", responses = {
            @ApiResponse(responseCode = "200", description = "Return game state",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Game.class))})
    })
    @GetMapping("/state")
    public ResponseEntity<Game> getGame() {
        return ResponseEntity.ok(game);
    }

    @Operation(summary = "Make a move with a number from 1-9", responses = {
            @ApiResponse(responseCode = "200", description = "Valid move",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Game.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid move",
                    content = @Content)
    })
    @PostMapping("/make-move")
    public ResponseEntity<Game> makeMove(@RequestBody Integer tileNumber) {
        if (!game.validMove(tileNumber)) {
            throw new InvalidMoveException("This tileNumber is invalid: %s".formatted(tileNumber));
        }

        game.makeMove(tileNumber);
        return ResponseEntity.ok(game);
    }

    @Operation(summary = "Reset the game to initial state", responses = {
            @ApiResponse(responseCode = "200", description = "Reset game state",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Game.class))})
    })
    @PostMapping("/reset")
    public ResponseEntity<Game> reset() {
        game.reset();
        return ResponseEntity.ok(game);
    }

    @ExceptionHandler(InvalidMoveException.class)
    public ResponseEntity<Map<String, String>> handleInvalidMoveException(InvalidMoveException e) {
        Map<String, String> errorResponse = Map.of("message", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
