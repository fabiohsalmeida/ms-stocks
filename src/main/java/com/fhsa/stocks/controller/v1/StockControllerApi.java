package com.fhsa.stocks.controller.v1;

import com.fhsa.stocks.dto.request.StockRequest;
import com.fhsa.stocks.dto.response.StockAverageResponse;
import com.fhsa.stocks.dto.response.StockResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Api(tags = "Stocks API")
@RequestMapping("/v1/stock")
public interface StockControllerApi {

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("Includes many stocks through a text/file")
    void includeManyStocks(@RequestParam(required = true) MultipartFile file)
            throws IOException;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Include a new stock manually")
    StockResponse includeStock(@RequestBody @Valid StockRequest stockRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("List all stocks")
    List<StockResponse> listStocks();

    @GetMapping("/code/{stockCode}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get stock by stock's code")
    StockResponse getStockByCode(@PathVariable String stockCode);

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get stock by stock's name")
    StockResponse getStockByName(@RequestParam(required = true) String stockName);

    @DeleteMapping("/{stockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Remove stock by code")
    void removeStockByCode(@PathVariable String stockId);

    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get top 10")
    List<StockAverageResponse> getAveragePriceFromTopStocks(
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "theoreticalAmount") String sortedDescBy);
}
