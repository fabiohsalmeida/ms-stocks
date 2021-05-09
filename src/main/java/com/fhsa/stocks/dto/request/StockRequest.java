package com.fhsa.stocks.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockRequest {

    @ApiModelProperty("Stock's code")
    private String code;
    @ApiModelProperty("Stock's name")
    private String name;
    @ApiModelProperty("Theoretical amount")
    private Long theoreticalAmount;
    @ApiModelProperty("Participation percentage")
    private BigDecimal participation;
}
