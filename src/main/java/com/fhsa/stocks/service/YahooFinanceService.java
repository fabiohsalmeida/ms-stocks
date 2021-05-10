package com.fhsa.stocks.service;

import com.fhsa.stocks.client.YahooFinanceClient;
import com.fhsa.stocks.client.dto.response.YahooFinanceAutoCompleteResponse;
import com.fhsa.stocks.client.dto.YahooFinanceGetQuoteResult;
import com.fhsa.stocks.client.dto.response.YahooFinanceGetQuotesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class YahooFinanceService {

    @Value("${feign.yahoo-financial.key}")
    private String apiKey;
    @Value("${feign.yahoo-financial.host}")
    private String apiHost;
    @Value("${feign.yahoo-financial.region}")
    private String defaultRegion;
    private final YahooFinanceClient client;

    public YahooFinanceService(YahooFinanceClient client) {
        this.client = client;
    }

    public String getSymbol(String query) {
        YahooFinanceAutoCompleteResponse autoCompleteResponse = client.consultAutoComplete(
                apiKey, apiHost, Boolean.TRUE, defaultRegion, query);

        return autoCompleteResponse.getQuotes().stream().findFirst().get().getSymbol();
    }

    public YahooFinanceGetQuoteResult getAveragePrices(String... symbol) {
        YahooFinanceGetQuotesResponse quotesResponse = client.consultQuotes(apiKey, apiHost, true,
                defaultRegion, symbol);

        return quotesResponse.getQuoteResponse().getResult().stream().findFirst().orElse(null);
    }
}
