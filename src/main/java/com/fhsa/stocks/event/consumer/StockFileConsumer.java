package com.fhsa.stocks.event.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.service.StockService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class StockFileConsumer {

    private static final String TOPIC = "stock_file_process";
    private static final String GROUP_ID = "stock-file";
    private final ObjectReader stockFileObjectReader;
    private final StockService service;

    public StockFileConsumer(ObjectReader stockFileObjectReader,
                             StockService service) {
        this.stockFileObjectReader = stockFileObjectReader;
        this.service = service;
    }

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consumer(@Payload String payload) throws JsonProcessingException {
        StockRequest request = stockFileObjectReader.readValue(payload);

        service.includeStockFromFile(request);
    }
}
