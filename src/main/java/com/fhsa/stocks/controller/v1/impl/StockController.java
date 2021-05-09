package com.fhsa.stocks.controller.v1.impl;

import com.fhsa.stocks.controller.v1.StockControllerApi;
import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.dto.response.StockResponse;
import com.fhsa.stocks.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class StockController implements StockControllerApi {

    private StockService service;

    public StockController(StockService service) {
        this.service = service;
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
}
