package com.fhsa.stocks.client.dto.response;

import com.fhsa.stocks.client.dto.YahooFinanceAutoCompleteQuote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YahooFinanceAutoCompleteResponse {

    private Integer count;
    private List<YahooFinanceAutoCompleteQuote> quotes;
}
