package com.fhsa.stocks.controller.v1.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.repository.StockRepository;
import com.fhsa.stocks.service.StockService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockController.class)
class StockControllerTest {

    private static final String STOCK_NAME_FIELD = "stockName";
    private static final String STOCK_NAME_VALUE = "Stock S/A Name";
    private static final String FILE_NAME = "file";
    private static final String FILE_ORIGINAL_NAME = "test.txt";
    private static final String FILE_LINE_CONTENT = "TSTSA;TEST S/A;4355174839;3.096";

    @Autowired
    private MockMvc mockMvc;

    private final EasyRandom generator = new EasyRandom();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private StockService service;

    @MockBean
    private StockRepository repository;

    @ParameterizedTest
    @ValueSource(strings = {"/v1/stock", "/v1/stock/code/ANYCOD", "/v1/stock/top"})
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
    void shouldIncludeStockInvalidName() throws  Exception {
        var stock = generator.nextObject(StockRequest.class);

        stock.setName(null);

        var requestPayload = objectMapper.writeValueAsString(stock);

        mockMvc.perform(post("/v1/stock")
                .content(requestPayload)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldIncludeStockInvalidCode() throws  Exception {
        var stock = generator.nextObject(StockRequest.class);

        stock.setCode(null);

        var requestPayload = objectMapper.writeValueAsString(stock);

        mockMvc.perform(post("/v1/stock")
                .content(requestPayload)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteStock() throws Exception {
        mockMvc.perform(delete("/v1/stock/609739068976a7053388cf8a"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldIncludeManyStocks() throws Exception {
        MockMultipartFile file = new MockMultipartFile(FILE_NAME, FILE_ORIGINAL_NAME,
                MediaType.TEXT_PLAIN_VALUE, FILE_LINE_CONTENT.getBytes());

        mockMvc.perform(multipart("/v1/stock/file")
                .file(file))
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldGetAveragePriceByLimit() throws Exception {
        mockMvc.perform(get("/v1/stock/top?limit=11"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAveragePriceByColumn() throws Exception {
        mockMvc.perform(get("/v1/stock/top?sortedBy=name"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAveragePriceByLimitAndColumn() throws Exception {
        mockMvc.perform(get("/v1/stock/top?limit=11&sortedBy=name"))
                .andExpect(status().isOk());
    }
}
