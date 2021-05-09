package com.fhsa.stocks.factory;

import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.entity.StockEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class StockFactoryTest {

    @InjectMocks
    private StockFactory factory;

    private EasyRandom generator = new EasyRandom();

    @Test
    void validCreateResponseFromEntity() {
        var stockEntity = generator.nextObject(StockEntity.class);

        var stockResponse = factory.createResponseFromEntity(stockEntity);

        assertEquals(stockEntity.getId(), stockResponse.getId());
        assertEquals(stockEntity.getCode(), stockResponse.getCode());
        assertEquals(stockEntity.getName(), stockResponse.getName());
        assertEquals(stockEntity.getTheoreticalAmount(), stockResponse.getTheoreticalAmount());
        assertEquals(stockEntity.getParticipation(), stockResponse.getParticipation());
    }

    @Test
    void validCreateEntityFromRequest() {
        var stockRequest = generator.nextObject(StockRequest.class);

        var stockEntity = factory.createEntityFromRequest(stockRequest);

        assertNull(stockEntity.getId());
        assertEquals(stockRequest.getCode(), stockEntity.getCode());
        assertEquals(stockRequest.getName(), stockEntity.getName());
        assertEquals(stockRequest.getTheoreticalAmount(), stockEntity.getTheoreticalAmount());
        assertEquals(stockRequest.getParticipation(), stockEntity.getParticipation());
    }

    @Test
    void validCreateListResponseFromListEntity() {
        var stockEntityList = getStockEntityList();

        var stockResponseList = factory.createListResponseFromListEntity(stockEntityList);

        assertEquals(stockEntityList.size(), stockResponseList.size());
    }

    private List<StockEntity> getStockEntityList() {
        return List.of(generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class));
    }
}
