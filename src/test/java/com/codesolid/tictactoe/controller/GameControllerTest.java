package com.codesolid.tictactoe.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GameControllerTest {

    private final static String JSON_DEFAULT_STATE = "{\"tiles\":[[null,null,null],[null,null,null],[null,null,null]],\"gameState\":null,\"finished\":false,\"currentPlayer\":\"x\"}";
    private final static String JSON_AFTER_MOVE = "{\"tiles\":[[null,null,null],[null,\"x\",null],[null,null,null]],\"gameState\":null,\"finished\":false,\"currentPlayer\":\"o\"}";
    private final static String JSON_INVALID = "{\"message\":\"This tileNumber is invalid: 5\"}";
    @Autowired
    private MockMvc mvc;

    @Test
    void getGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/game/state");
        MvcResult result = mvc.perform(request).andReturn();

        assertEquals(JSON_DEFAULT_STATE, result.getResponse().getContentAsString());
    }

    @Test
    void makeMove() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/api/game/make-move")
                .contentType(MediaType.APPLICATION_JSON)
                .content("5");
        MvcResult result = mvc.perform(request).andReturn();

        assertEquals(JSON_AFTER_MOVE, result.getResponse().getContentAsString());
    }

    @Test
    void makeInvalidMove() throws Exception {
        // First request is valid, all tiles are empty
        RequestBuilder request = MockMvcRequestBuilders.post("/api/game/make-move")
                .contentType(MediaType.APPLICATION_JSON)
                .content("5");
        mvc.perform(request).andReturn();

        // Same request, so tile 5 is unavailable.
        RequestBuilder invalidRequest = MockMvcRequestBuilders.post("/api/game/make-move")
                .contentType(MediaType.APPLICATION_JSON)
                .content("5");
        MvcResult invalidResult = mvc.perform(invalidRequest).andReturn();

        assertEquals(JSON_INVALID, invalidResult.getResponse().getContentAsString());
    }

    @Test
    void resetGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/api/game/make-move")
                .contentType(MediaType.APPLICATION_JSON)
                .content("6");
        mvc.perform(request).andReturn();

        RequestBuilder resetRequest = MockMvcRequestBuilders.post("/api/game/reset");
        MvcResult resetResult = mvc.perform(resetRequest).andReturn();

        assertEquals(JSON_DEFAULT_STATE, resetResult.getResponse().getContentAsString());
    }
}