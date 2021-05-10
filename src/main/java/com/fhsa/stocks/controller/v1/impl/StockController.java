package com.fhsa.stocks.controller.v1.impl;

import com.fhsa.stocks.controller.v1.StockControllerApi;
import com.fhsa.stocks.dto.Error;
import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.dto.response.StockAverageResponse;
import com.fhsa.stocks.dto.response.StockResponse;
import com.fhsa.stocks.entity.StockEntity;
import com.fhsa.stocks.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class StockController implements StockControllerApi {

    private StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @Override
    public void includeManyStocks(MultipartFile file) throws IOException {
        service.includeManyStocks(file);
    }

    @Override
    public StockResponse includeStock(StockRequest stockRequest) {
        return service.includeStock(stockRequest);
    }

    // TODO Page
    @Override
    public List<StockResponse> listStocks() {
        return service.listStocks();
    }

    @Override
    public StockResponse getStockByCode(String stockCode) {
        return service.getStockByCode(stockCode);
    }

    @Override
    public StockResponse getStockByName(String stockName) {
        return service.getStockByName(stockName);
    }

    @Override
    public void removeStockByCode(String stockId) {
        service.removeStock(stockId);
    }

    @Override
    public List<StockAverageResponse> getAveragePriceFromTopStocks(Integer limit, String sortedDescBy) {
        return service.getAveragePriceFromTopStocks(limit, sortedDescBy);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().stream().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return Error.builder()
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }
}
