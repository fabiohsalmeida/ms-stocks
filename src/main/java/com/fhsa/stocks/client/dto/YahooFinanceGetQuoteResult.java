package com.fhsa.stocks.client.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class YahooFinanceGetQuoteResult {

    private String currency;
    private BigDecimal fiftyDayAverage;
    private BigDecimal twoHundredDayAverage;
}
