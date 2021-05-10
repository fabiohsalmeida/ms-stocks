package com.fhsa.stocks.dto.request;

import com.fhsa.stocks.annotation.UniqueStockCode;
import com.fhsa.stocks.annotation.UniqueStockName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class StockRequest {

    @NotBlank
    @UniqueStockCode
    @ApiModelProperty("Stock's code")
    private String code;
    @NotBlank
    @UniqueStockName
    @ApiModelProperty("Stock's name")
    private String name;
    @NotNull
    @ApiModelProperty("Theoretical amount")
    private Long theoreticalAmount;
    @NotNull
    @ApiModelProperty("Participation percentage")
    private BigDecimal participation;
}
