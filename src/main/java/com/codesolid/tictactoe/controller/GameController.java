package com.codesolid.tictactoe.controller;

import com.codesolid.tictactoe.exceptions.InvalidMoveException;
import com.codesolid.tictactoe.model.Game;
import com.codesolid.tictactoe.model.GameRecord;
import com.codesolid.tictactoe.service.GameRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final Game game;
    private final GameRecordService gameRecordService;

    public GameController(Game game, GameRecordService gameRecordService) {
        this.game = game;
        this.gameRecordService = gameRecordService;
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

        if (game.isFinished()) {
            gameRecordService.create(new GameRecord(
                    game.getGameState(),
                    game.getMoveCount(),
                    LocalDateTime.now()
            ));
        }

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

    @Operation(summary = "Fetch all records", responses = {
            @ApiResponse(responseCode = "200", description = "All records are successfully fetched",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameRecord.class))})
    })
    @GetMapping("/records")
    public ResponseEntity<List<GameRecord>> fetchGameRecords() {
        return ResponseEntity.ok(gameRecordService.findAll());
    }

    @ExceptionHandler(InvalidMoveException.class)
    public ResponseEntity<Map<String, String>> handleInvalidMoveException(InvalidMoveException e) {
        Map<String, String> errorResponse = Map.of("message", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
