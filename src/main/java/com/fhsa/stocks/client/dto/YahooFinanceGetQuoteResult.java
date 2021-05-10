package com.fhsa.stocks.client.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class YahooFinanceGetQuoteResult {

    private String currency;
    private BigDecimal fiftyDayAverage;
    private BigDecimal twoHundredDayAverage;
}
