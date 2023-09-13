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

    @Autowired
    private MockMvc mvc;

    @Test
    void getGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/game/state");
        MvcResult result = mvc.perform(request).andReturn();

        String expected = "{\"tiles\":[[null,null,null],[null,null,null],[null,null,null]],\"gameState\":null,\"finished\":false,\"currentPlayer\":\"x\"}";

        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    void makeMove() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/api/game/make-move")
                .contentType(MediaType.APPLICATION_JSON)
                .content("5");
        MvcResult result = mvc.perform(request).andReturn();

        String expected = "{\"tiles\":[[null,null,null],[null,\"x\",null],[null,null,null]],\"gameState\":null,\"finished\":false,\"currentPlayer\":\"o\"}";

        assertEquals(expected, result.getResponse().getContentAsString());
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

        String expected = "{\"message\":\"This tileNumber is invalid: 5\"}";

        assertEquals(expected, invalidResult.getResponse().getContentAsString());
    }
}