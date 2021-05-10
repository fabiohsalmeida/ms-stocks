package com.fhsa.stocks.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "$(feign.yahoo-financial.name)", url = "$(feign.yahoo-financial.url)")
public interface YahooFinanceClient {


}
