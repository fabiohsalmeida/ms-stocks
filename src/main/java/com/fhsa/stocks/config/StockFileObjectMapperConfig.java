package com.fhsa.stocks.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fhsa.stocks.dto.request.StockRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockFileObjectMapperConfig {

    @Bean
    public ObjectReader createDefaultStockFileMapper() {
        CsvMapper mapper = new CsvMapper();

        return mapper.readerFor(StockRequest.class).with(getSchema(mapper));
    }

    private CsvSchema getSchema(CsvMapper mapper) {
        return mapper.typedSchemaFor(StockRequest.class)
                .withColumnSeparator(';')
                .withoutHeader()
                .sortedBy("code", "name", "theoreticalAmount", "participation")
                .withoutQuoteChar();
    }
}
