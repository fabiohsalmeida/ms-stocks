package com.fhsa.stocks.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YahooFinanceGetQuote {

    List<YahooFinanceGetQuoteResult> result;
}