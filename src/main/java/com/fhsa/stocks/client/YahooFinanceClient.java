package com.fhsa.stocks.client;

import com.fhsa.stocks.client.dto.response.YahooFinanceAutoCompleteResponse;
import com.fhsa.stocks.client.dto.response.YahooFinanceGetQuotesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.yahoo-financial.name}", url = "${feign.yahoo-financial.url}")
public interface YahooFinanceClient {

    final String API_KEY_HEADER = "x-rapidapi-key";
    final String API_HOST_HEADER = "x-rapidapi-hos";
    final String USE_QUERY_HEADER = "useQueryString";

    @GetMapping("/auto-complete")
    YahooFinanceAutoCompleteResponse consultAutoComplete(
            @RequestHeader(API_KEY_HEADER) String apiKey,
            @RequestHeader(API_HOST_HEADER) String apiHost,
            @RequestHeader(USE_QUERY_HEADER) Boolean useQuery,
            @RequestParam String region,
            @RequestParam String q);

    @GetMapping("/market/v2/get-quotes")
    YahooFinanceGetQuotesResponse consultQuotes(
            @RequestHeader(API_KEY_HEADER) String apiKey,
            @RequestHeader(API_HOST_HEADER) String apiHost,
            @RequestHeader(USE_QUERY_HEADER) Boolean useQuery,
            @RequestParam String region,
            @RequestParam String... symbols);
}
