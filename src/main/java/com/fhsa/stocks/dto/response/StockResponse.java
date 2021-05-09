package com.fhsa.stocks.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StockResponse {

    @ApiModelProperty("Stock's ID on database")
    private String id;
    @ApiModelProperty("Stock's code")
    private String code;
    @ApiModelProperty("Stock's name")
    private String name;
    @ApiModelProperty("Theoretical amount")
    private Long theoreticalAmount;
    @ApiModelProperty("Participation percentage")
    private BigDecimal participation;
}
