package com.fhsa.stocks.factory;

import com.fhsa.stocks.client.dto.YahooFinanceGetQuoteResult;
import com.fhsa.stocks.dto.response.StockAverageResponse;
import com.fhsa.stocks.entity.StockEntity;
import org.springframework.stereotype.Component;

@Component
public class StockAverageFactory {

    private static final String ERROR_SYMBOL_NOT_FOUND = "Symbol not found by auto complete operation.";
    private static final String ERROR_GET_QUOTE_NOT_FOUND = "Quote not found by get quote not found operation.";

    public StockAverageResponse createStockAverageFromEntity(StockEntity stockEntity,
                                                             YahooFinanceGetQuoteResult quoteResult) {
        var stockAverageResponse = this.createTemplateStockAverageResponse(stockEntity);

        stockAverageResponse.setCurrency(quoteResult.getCurrency());
        stockAverageResponse.setFiftyDayAverage(quoteResult.getFiftyDayAverage());
        stockAverageResponse.setTwoHundredDayAverage(quoteResult.getTwoHundredDayAverage());

        return stockAverageResponse;
    }

    public StockAverageResponse createStockAverageFromEntityNonSymbol(StockEntity entity) {
        var stockAverageResponse = this.createTemplateStockAverageResponse(entity);

        stockAverageResponse.setError(ERROR_SYMBOL_NOT_FOUND);

        return stockAverageResponse;
    }

    public StockAverageResponse createStockAverageFromEntityNonPrices(StockEntity entity) {
        var stockAverageResponse = this.createTemplateStockAverageResponse(entity);

        stockAverageResponse.setError(ERROR_GET_QUOTE_NOT_FOUND);

        return stockAverageResponse;
    }

    private StockAverageResponse createTemplateStockAverageResponse(StockEntity entity) {
        var stockAverageResponse = new StockAverageResponse();

        stockAverageResponse.setId(entity.getId());
        stockAverageResponse.setCode(entity.getCode());
        stockAverageResponse.setName(entity.getName());
        stockAverageResponse.setTheoreticalAmount(entity.getTheoreticalAmount());
        stockAverageResponse.setParticipation(entity.getParticipation());

        return stockAverageResponse;
    }
}
