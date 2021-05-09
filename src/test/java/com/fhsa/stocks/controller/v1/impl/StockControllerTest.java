package com.fhsa.stocks.controller.v1.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.service.StockService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockController.class)
public class StockControllerTest {

    private static final String STOCK_NAME_FIELD = "stockName";
    private static final String STOCK_NAME_VALUE = "Stock S/A Name";

    @Autowired
    private MockMvc mockMvc;

    private final EasyRandom generator = new EasyRandom();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private StockService service;

    @ParameterizedTest
    @ValueSource(strings = {"/v1/stock", "/v1/stock/code/ANYCOD"})
    void shouldGetOk(String arg) throws Exception {
        mockMvc.perform(get(arg))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetOkByName() throws  Exception {
        mockMvc.perform(get("/v1/stock/name")
                .queryParam(STOCK_NAME_FIELD, STOCK_NAME_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void shouldIncludeStock() throws  Exception {
        var stock = generator.nextObject(StockRequest.class);
        var requestPayload = objectMapper.writeValueAsString(stock);

        mockMvc.perform(post("/v1/stock")
                .content(requestPayload)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteStock() throws Exception {
        mockMvc.perform(delete("/v1/stock/609739068976a7053388cf8a"))
                .andExpect(status().isNoContent());
    }
}
