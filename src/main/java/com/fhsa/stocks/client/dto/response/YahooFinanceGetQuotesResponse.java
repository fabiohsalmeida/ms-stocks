package com.fhsa.stocks.client.dto.response;

import com.fhsa.stocks.client.dto.YahooFinanceGetQuote;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooFinanceGetQuotesResponse {

    private YahooFinanceGetQuote quoteResponse;
}
