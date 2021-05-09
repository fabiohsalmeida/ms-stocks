package com.fhsa.stocks.factory;

import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.dto.response.StockResponse;
import com.fhsa.stocks.entity.StockEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockFactory {

    public StockEntity createEntityFromRequest(StockRequest request) {
        return StockEntity.builder()
                .code(request.getCode())
                .name(request.getName())
                .theoreticalAmount(request.getTheoreticalAmount())
                .participation(request.getParticipation())
                .build();
    }

    public StockResponse createResponseFromEntity(StockEntity stockEntity) {
        return StockResponse.builder()
                .id(stockEntity.getId())
                .code(stockEntity.getCode())
                .name(stockEntity.getName())
                .theoreticalAmount(stockEntity.getTheoreticalAmount())
                .participation(stockEntity.getParticipation())
                .build();
    }

    public List<StockResponse> createListResponseFromListEntity(List<StockEntity> stockEntityList) {
        return stockEntityList.stream()
                .map(e -> new StockResponse(e.getId(), e.getCode(), e.getName(),
                        e.getTheoreticalAmount(), e.getParticipation()))
                .collect(Collectors.toList());
    }
}
