package com.codesolid.tictactoe.service;

import com.codesolid.tictactoe.model.GameRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(scripts = "/data.sql")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GameRecordServiceTest {

    private JdbcClient jdbcClient;
    private GameRecordService gameRecordService;

    @Autowired
    public GameRecordServiceTest(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
        gameRecordService = new GameRecordService(jdbcClient);
    }

    @Test
    void findAll() {
        List<GameRecord> records = gameRecordService.findAll();
        assertEquals(5, records.size());
    }

    @Test
    void create() {
        String gameState = "Winner is x";
        Integer moveCount = 7;
        LocalDateTime date = LocalDateTime.of(2023, 9, 14, 16, 28, 28);

        GameRecord gameRecord = new GameRecord(gameState, moveCount, date);
        gameRecordService.create(gameRecord);

        List<GameRecord> records = gameRecordService.findAll();
        assertEquals(6, records.size());

        assertEquals(gameState, records.get(5).getGameState());
        assertEquals(moveCount, records.get(5).getMoveCount());
        assertEquals(date, records.get(5).getDate());
    }
}