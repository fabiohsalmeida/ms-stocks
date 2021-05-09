package com.fhsa.stocks.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Document("stock")
public class StockEntity {

    @Id
    @ReadOnlyProperty
    private String id;
    private String code;
    private String name;
    private Long theoreticalAmount;
    private BigDecimal participation;
}
