package com.codesolid.tictactoe.service;

import com.codesolid.tictactoe.model.GameRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class GameRecordService {

    private static final Logger log = LoggerFactory.getLogger(GameRecordService.class);

    private final JdbcClient jdbcClient;

    public GameRecordService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<GameRecord> findAll() {
        return jdbcClient.sql("SELECT id, game_state, move_count, date FROM game_record")
                .query(GameRecord.class)
                .list();
    }

    public void create(GameRecord record) {
        int created = jdbcClient.sql("INSERT INTO game_record(game_state, move_count, date) values (?, ?, ?)")
                .params(List.of(record.getGameState(), record.getMoveCount(), record.getDate()))
                .update();

        Assert.state( created == 1, "Failed to create game record");
    }
}
