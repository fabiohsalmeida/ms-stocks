package com.fhsa.stocks.service;

import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.dto.response.StockResponse;
import com.fhsa.stocks.entity.StockEntity;
import com.fhsa.stocks.factory.StockFactory;
import com.fhsa.stocks.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StockService {

    private StockRepository repository;
    private StockFactory factory;

    public StockService(StockRepository repository,
                        StockFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public StockResponse includeStock(StockRequest stockRequest) {
        StockEntity stockEntity = factory.createEntityFromRequest(stockRequest);

        stockEntity = repository.save(stockEntity);

        return factory.createResponseFromEntity(stockEntity);
    }

    public List<StockResponse> listStocks() {
        List<StockEntity> stockEntityList = repository.findAll();

        return factory.createListResponseFromListEntity(stockEntityList);
    }

    public StockResponse getStockByCode(String stockCode) {
        StockEntity stockEntity = repository.findByCode(stockCode);

        return factory.createResponseFromEntity(stockEntity);
    }

    public StockResponse getStockByName(String stockName) {
        StockEntity stockEntity = repository.findByName(stockName);

        return factory.createResponseFromEntity(stockEntity);
    }

    public void removeStock(String stockId) {
        repository.deleteById(stockId);
    }
}
