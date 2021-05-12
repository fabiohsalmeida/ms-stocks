package com.fhsa.stocks.service;

import com.fhsa.stocks.client.dto.YahooFinanceGetQuoteResult;
import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.dto.response.StockAverageResponse;
import com.fhsa.stocks.dto.response.StockResponse;
import com.fhsa.stocks.entity.StockEntity;
import com.fhsa.stocks.event.producer.StockFileProducer;
import com.fhsa.stocks.factory.StockAverageFactory;
import com.fhsa.stocks.factory.StockFactory;
import com.fhsa.stocks.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockService {

    private static final Integer DEFAULT_FIRST_PAGE_NUMBER = 0;

    private StockRepository repository;
    private StockFactory factory;
    private StockFileProducer producer;
    private YahooFinanceService yahooFinanceService;
    private StockAverageFactory averageFactory;

    public StockService(StockRepository repository,
                        StockFactory factory,
                        StockFileProducer producer,
                        YahooFinanceService yahooFinanceService,
                        StockAverageFactory averageFactory) {
        this.repository = repository;
        this.factory = factory;
        this.producer = producer;
        this.yahooFinanceService = yahooFinanceService;
        this.averageFactory = averageFactory;
    }

    public StockResponse includeStock(StockRequest stockRequest) {
        var stockEntity = factory.createEntityFromRequest(stockRequest);

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
        var stockEntity = repository.findByCode(stockCode);

        return factory.createResponseFromEntity(stockEntity);
    }

    public StockResponse getStockByName(String stockName) {
        var stockEntity = repository.findByName(stockName);

        return factory.createResponseFromEntity(stockEntity);
    }

    public void removeStock(String stockId) {
        repository.deleteById(stockId);
    }

    public void includeManyStocks(MultipartFile file) throws IOException {
        var inputStream = file.getInputStream();

        processFile(inputStream);
    }

    public List<StockAverageResponse> getAveragePriceFromTopStocks(Integer limit, String sortedDescBy) {
        var sort = Sort.by(Sort.Direction.DESC, sortedDescBy);

        var pageable = PageRequest.of(DEFAULT_FIRST_PAGE_NUMBER,limit, sort);

        List<StockEntity> stockEntityList = repository.findAll(pageable).getContent();

        return stockEntityList.stream()
                .map(this::mapStockAverage)
                .collect(Collectors.toList());
    }

    private StockAverageResponse mapStockAverage(StockEntity entity) {
        String symbol = yahooFinanceService.getSymbol(entity.getCode());

        return StringUtils.isEmpty(symbol)
                ? averageFactory.createStockAverageFromEntityNonSymbol(entity)
                : getAveragePrices(entity, symbol);
    }

    private StockAverageResponse getAveragePrices(StockEntity entity, String symbol) {
        YahooFinanceGetQuoteResult quoteResult = yahooFinanceService.getAveragePrices(symbol);

        return quoteResult == null
                ? averageFactory.createStockAverageFromEntityNonPrices(entity)
                : averageFactory.createStockAverageFromEntity(entity, quoteResult);
    }

    private boolean isValidNameAndCode(String name, String code) {
        return repository.findByName(name) == null && repository.findByCode(code) == null;
    }

    private void processFile(InputStream inputStream) {
        var streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        new BufferedReader(streamReader).lines().forEach(this::processFileLine);
    }

    private void processFileLine(String line) {

        producer.send(line);
    }
}