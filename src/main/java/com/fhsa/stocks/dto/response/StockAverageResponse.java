package com.fhsa.stocks.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class StockAverageResponse extends StockResponse {

    private String currency;
    private BigDecimal fiftyDayAverage;
    private BigDecimal twoHundredDayAverage;
    private String error;
}
