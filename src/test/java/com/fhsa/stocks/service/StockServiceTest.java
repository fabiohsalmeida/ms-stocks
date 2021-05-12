package com.fhsa.stocks.service;

import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.dto.response.StockResponse;
import com.fhsa.stocks.entity.StockEntity;
import com.fhsa.stocks.event.producer.StockFileProducer;
import com.fhsa.stocks.factory.StockAverageFactory;
import com.fhsa.stocks.factory.StockFactory;
import com.fhsa.stocks.repository.StockRepository;
import org.bson.types.ObjectId;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    private static final String STOCK_CODE = "STCKCD";
    private static final String STOCK_NAME = "Stock S/A Name";
    private static final String STOCK_ID = (new ObjectId()).toString();
    private static final String FILE_NAME = "file";
    private static final String FILE_ORIGINAL_NAME = "test.txt";
    private static final String FILE_LINE_CONTENT = "TSTSA;TEST S/A;4355174839;3.096";

    @InjectMocks
    private StockService service;

    @Mock
    private YahooFinanceService financeService;

    @Mock
    private StockRepository repository;

    @Mock
    private StockFactory factory;

    @Mock
    private StockAverageFactory averageFactory;

    @Mock
    private StockFileProducer fileProducer;

    private final EasyRandom generator = new EasyRandom();

    @Test
    void validIncludeStock() {
        var request = generator.nextObject(StockRequest.class);
        var entity = createEntityFromRequest(request);
        var entityWithId = putEntityId(entity);
        var expectedResponse = createResponseFromEntity(entity);

        when(factory.createEntityFromRequest(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entityWithId);
        when(factory.createResponseFromEntity(entityWithId)).thenReturn(expectedResponse);

        var response = service.includeStock(request);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getName(), response.getName());
        assertEquals(expectedResponse.getTheoreticalAmount(), response.getTheoreticalAmount());
        assertEquals(expectedResponse.getParticipation(), response.getParticipation());
    }

    @Test
    void validListStocks() {
        var entityList = createEntityList();
        var expectedResponseList = createResponseList(entityList);

        when(repository.findAll()).thenReturn(entityList);
        when(factory.createListResponseFromListEntity(entityList)).thenReturn(expectedResponseList);

        var responseList = service.listStocks();

        for(int i = 0; i < expectedResponseList.size(); i++) {
            StockResponse expectedResponse = expectedResponseList.get(i);
            StockResponse response = responseList.get(i);

            assertEquals(expectedResponse.getId(), response.getId());
            assertEquals(expectedResponse.getCode(), response.getCode());
            assertEquals(expectedResponse.getName(), response.getName());
            assertEquals(expectedResponse.getTheoreticalAmount(), response.getTheoreticalAmount());
            assertEquals(expectedResponse.getParticipation(), response.getParticipation());
        }
    }

    @Test
    void validGetStockByCode() {
        var entity = generator.nextObject(StockEntity.class);
        var expectedResponse = createResponseFromEntity(entity);

        when(repository.findByCode(STOCK_CODE)).thenReturn(entity);
        when(factory.createResponseFromEntity(entity)).thenReturn(expectedResponse);

        var response = service.getStockByCode(STOCK_CODE);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getName(), response.getName());
        assertEquals(expectedResponse.getTheoreticalAmount(), response.getTheoreticalAmount());
        assertEquals(expectedResponse.getParticipation(), response.getParticipation());
    }

    @Test
    void validGetStockByName() {
        var entity = generator.nextObject(StockEntity.class);
        var expectedResponse = createResponseFromEntity(entity);

        when(repository.findByName(STOCK_NAME)).thenReturn(entity);
        when(factory.createResponseFromEntity(entity)).thenReturn(expectedResponse);

        var response = service.getStockByName(STOCK_NAME);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getName(), response.getName());
        assertEquals(expectedResponse.getTheoreticalAmount(), response.getTheoreticalAmount());
        assertEquals(expectedResponse.getParticipation(), response.getParticipation());
    }

    @Test
    void validRemoveStock() {
        service.removeStock(STOCK_ID);

        verify(repository, times(1)).deleteById(STOCK_ID);
    }

    @Test
    void validIncludeStockFromFile() {
        var request = generator.nextObject(StockRequest.class);
        var entity = generator.nextObject(StockEntity.class);
        var expectedResponse = generator.nextObject(StockResponse.class);

        when(repository.findByName(request.getName())).thenReturn(null);
        when(repository.findByCode(request.getCode())).thenReturn(null);
        when(factory.createEntityFromRequest(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(factory.createResponseFromEntity(entity)).thenReturn(expectedResponse);

        service.includeStockFromFile(request);

        verify(repository, times(1)).findByName(request.getName());
        verify(repository, times(1)).findByCode(request.getCode());
        verify(factory, times(1)).createEntityFromRequest(request);
        verify(repository, times(1)).save(entity);
        verify(factory, times(1)).createResponseFromEntity(entity);
    }

    @Test
    void invalidNameIncludeStockFromFile() {
        var request = generator.nextObject(StockRequest.class);

        when(repository.findByName(request.getName())).thenReturn(generator.nextObject(StockEntity.class));

        service.includeStockFromFile(request);

        verify(repository, times(1)).findByName(request.getName());
        verify(repository, never()).findByCode(any());
    }

    @Test
    void invalidCodeIncludeStockFromFile() {
        var request = generator.nextObject(StockRequest.class);

        when(repository.findByName(request.getName())).thenReturn(null);
        when(repository.findByCode(request.getCode())).thenReturn(generator.nextObject(StockEntity.class));

        service.includeStockFromFile(request);

        verify(repository, times(1)).findByName(request.getName());
        verify(repository, times(1)).findByCode(request.getCode());
        verify(factory, never()).createEntityFromRequest(any());
    }

    @Test
    void validIncludeManyStocks() throws IOException {
        MockMultipartFile file = new MockMultipartFile(FILE_NAME, FILE_ORIGINAL_NAME,
                MediaType.TEXT_PLAIN_VALUE, FILE_LINE_CONTENT.getBytes());

        service.includeManyStocks(file);

        verify(fileProducer, times(1)).send(FILE_LINE_CONTENT);
    }

    private List<StockResponse> createResponseList(List<StockEntity> entityList) {
        return entityList.stream()
                .map(e -> new StockResponse(e.getId(), e.getCode(), e.getName(),
                        e.getTheoreticalAmount(), e.getParticipation()))
                .collect(Collectors.toList());
    }

    private List<StockEntity> createEntityList() {
        return List.of(generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class),
                generator.nextObject(StockEntity.class));
    }

    private StockEntity createEntityFromRequest(StockRequest request) {
        ObjectId id = new ObjectId();

        return StockEntity.builder()
                .id(id.toString())
                .code(request.getCode())
                .name(request.getName())
                .theoreticalAmount(request.getTheoreticalAmount())
                .participation(request.getParticipation())
                .build();
    }

    private StockEntity putEntityId(StockEntity entity) {
        ObjectId id = new ObjectId();

        entity.setId(id.toString());

        return entity;
    }

    private StockResponse createResponseFromEntity(StockEntity entity) {

        return StockResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .theoreticalAmount(entity.getTheoreticalAmount())
                .participation(entity.getParticipation())
                .build();
    }
}
