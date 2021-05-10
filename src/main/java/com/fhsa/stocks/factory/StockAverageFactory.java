package com.fhsa.stocks.factory;

import com.fhsa.stocks.client.dto.YahooFinanceGetQuoteResult;
import com.fhsa.stocks.dto.response.StockAverageResponse;
import com.fhsa.stocks.entity.StockEntity;
import org.springframework.stereotype.Component;

@Component
public class StockAverageFactory {

    public StockAverageResponse createStockAverageFromEntity(StockEntity stockResponse,
                                                               YahooFinanceGetQuoteResult quoteResult) {
        StockAverageResponse stockAverageResponse = new StockAverageResponse();

        stockAverageResponse.setId(stockResponse.getId());
        stockAverageResponse.setCode(stockResponse.getCode());
        stockAverageResponse.setName(stockResponse.getName());
        stockAverageResponse.setTheoreticalAmount(stockResponse.getTheoreticalAmount());
        stockAverageResponse.setParticipation(stockResponse.getParticipation());
        stockAverageResponse.setCurrency(quoteResult.getCurrency());
        stockAverageResponse.setFiftyDayAverage(quoteResult.getFiftyDayAverage());
        stockAverageResponse.setTwoHundredDayAverage(quoteResult.getTwoHundredDayAverage());

        return stockAverageResponse;
    }
}
