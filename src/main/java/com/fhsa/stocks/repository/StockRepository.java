package com.fhsa.stocks.repository;

import com.fhsa.stocks.entity.StockEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<StockEntity, String> {

    StockEntity findByCode(String code);
    StockEntity findByName(String name);
}
