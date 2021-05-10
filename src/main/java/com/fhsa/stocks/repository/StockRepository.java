package com.fhsa.stocks.repository;

import com.fhsa.stocks.entity.StockEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends MongoRepository<StockEntity, String> {

    StockEntity findByCode(String code);
    StockEntity findByName(String name);
    @Query(value = "{}")
    List<StockEntity> batata(Pageable pageable);
}
