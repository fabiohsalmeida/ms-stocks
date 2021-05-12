package com.fhsa.stocks.service;

import com.fhsa.stocks.client.YahooFinanceClient;
import com.fhsa.stocks.client.dto.YahooFinanceAutoCompleteQuote;
import com.fhsa.stocks.client.dto.YahooFinanceGetQuote;
import com.fhsa.stocks.client.dto.YahooFinanceGetQuoteResult;
import com.fhsa.stocks.client.dto.response.YahooFinanceAutoCompleteResponse;
import com.fhsa.stocks.client.dto.response.YahooFinanceGetQuotesResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YahooFinanceServiceTest {

    private static final Integer COUNT = 6;
    private static final String SYMBOL = "TSTSA";

    private YahooFinanceGetQuoteResult quoteResult;

    @InjectMocks
    private YahooFinanceService service;

    @Mock
    private YahooFinanceClient client;

    private final EasyRandom generator = new EasyRandom();

    @BeforeEach
    public void setUp() {
        quoteResult = generator.nextObject(YahooFinanceGetQuoteResult.class);
    }

    @Test
    void validGetSymbol() {
        var clientResponse = createDefaultAutoCompleteResponse();
        var query = "Any";

        when(client.consultAutoComplete(any(), any(), any(), any(), eq(query))).thenReturn(clientResponse);

        var response = service.getSymbol(query);

        assertEquals(SYMBOL, response);
    }

    @Test
    void validGetAveragePrices() {
        var quotesResponse = createDefaultQuotesResponse();

        when(client.consultQuotes(any(), any(), any(), any(), eq(SYMBOL))).thenReturn(quotesResponse);

        var response = service.getAveragePrices(SYMBOL);

        assertEquals(quoteResult, response);
    }

    private YahooFinanceGetQuotesResponse createDefaultQuotesResponse() {
        var response = new YahooFinanceGetQuotesResponse();
        var quote = new YahooFinanceGetQuote();

        quote.setResult(List.of(quoteResult));

        response.setQuoteResponse(quote);

        return response;
    }

    private YahooFinanceAutoCompleteResponse createDefaultAutoCompleteResponse() {
        var response = new YahooFinanceAutoCompleteResponse();

        response.setCount(COUNT);
        response.setQuotes(createDefaultAutoCompleteQuotes());

        return response;
    }

    private List<YahooFinanceAutoCompleteQuote> createDefaultAutoCompleteQuotes() {
        var autoCompleteQuote = new YahooFinanceAutoCompleteQuote();

        autoCompleteQuote.setSymbol(SYMBOL);

        return List.of(autoCompleteQuote,
                generator.nextObject(YahooFinanceAutoCompleteQuote.class));
    }
}
