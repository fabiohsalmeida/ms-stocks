package com.fhsa.stocks.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
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
