package com.fhsa.stocks.event.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockFileProducer {

    private static final String topic = "stock_file_process";

    private final KafkaTemplate<String, String> template;

    public StockFileProducer(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void send(String line) {
        template.send(topic, line);
    }
}
