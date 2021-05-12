package com.fhsa.stocks.factory;

import com.fhsa.stocks.client.dto.YahooFinanceGetQuoteResult;
import com.fhsa.stocks.entity.StockEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StockAverageFactoryTest {

    @InjectMocks
    private StockAverageFactory factory;

    private EasyRandom generator = new EasyRandom();

    @Test
    void validCreateStockAverageFromEntity() {
        var entity = generator.nextObject(StockEntity.class);
        var expectedResponse = generator.nextObject(YahooFinanceGetQuoteResult.class);

        var response = factory.createStockAverageFromEntity(entity, expectedResponse);

        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getName(), response.getName());
        assertEquals(entity.getCode(), response.getCode());
        assertEquals(entity.getTheoreticalAmount(), response.getTheoreticalAmount());
        assertEquals(entity.getParticipation(), response.getParticipation());
        assertEquals(expectedResponse.getCurrency(), response.getCurrency());
        assertEquals(expectedResponse.getFiftyDayAverage(), response.getFiftyDayAverage());
        assertEquals(expectedResponse.getTwoHundredDayAverage(), response.getTwoHundredDayAverage());
    }

    @Test
    void invalidCreateStockAverageEntityNonSymbol() {
        var entity = generator.nextObject(StockEntity.class);
        var expectedErrorMessage = "Symbol not found by auto complete operation.";

        var response = factory.createStockAverageFromEntityNonSymbol(entity);

        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getName(), response.getName());
        assertEquals(entity.getCode(), response.getCode());
        assertEquals(entity.getTheoreticalAmount(), response.getTheoreticalAmount());
        assertEquals(entity.getParticipation(), response.getParticipation());
        assertEquals(expectedErrorMessage, response.getError());
    }

    @Test
    void invalidCreateStockAverageEntityNonPrices() {
        var entity = generator.nextObject(StockEntity.class);
        var expectedErrorMessage = "Quote not found by get quote not found operation.";

        var response = factory.createStockAverageFromEntityNonPrices(entity);

        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getName(), response.getName());
        assertEquals(entity.getCode(), response.getCode());
        assertEquals(entity.getTheoreticalAmount(), response.getTheoreticalAmount());
        assertEquals(entity.getParticipation(), response.getParticipation());
        assertEquals(expectedErrorMessage, response.getError());
    }
}
