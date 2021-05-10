package com.fhsa.stocks.service;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.dto.response.StockResponse;
import com.fhsa.stocks.entity.StockEntity;
import com.fhsa.stocks.event.producer.StockFileProducer;
import com.fhsa.stocks.factory.StockFactory;
import com.fhsa.stocks.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class StockService {

    private StockRepository repository;
    private StockFactory factory;
    private StockFileProducer producer;
    private ObjectReader stockFileObjectReader;

    public StockService(StockRepository repository,
                        StockFactory factory,
                        StockFileProducer producer,
                        ObjectReader stockFileObjectReader) {
        this.repository = repository;
        this.factory = factory;
        this.producer = producer;
        this.stockFileObjectReader = stockFileObjectReader;
    }

    public StockResponse includeStock(StockRequest stockRequest) {
        StockEntity stockEntity = factory.createEntityFromRequest(stockRequest);

        stockEntity = repository.save(stockEntity);

        return factory.createResponseFromEntity(stockEntity);
    }

    public void includeStockFromFile(StockRequest stockRequest) {
        if (isValidNameAndCode(stockRequest.getName(), stockRequest.getCode())) {
            this.includeStock(stockRequest);
        }
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

    public void includeManyStocks(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        processFile(inputStream);
    }

    private boolean isValidNameAndCode(String name, String code) {
        return repository.findByName(name) == null && repository.findByCode(code) == null;
    }

    private void processFile(InputStream inputStream) {
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        new BufferedReader(streamReader).lines().forEach(this::processFileLine);
    }

    private void processFileLine(String line) {

        producer.send(line);
    }
}