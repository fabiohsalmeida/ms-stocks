package com.fhsa.stocks.validator;

import com.fhsa.stocks.annotation.UniqueStockCode;
import com.fhsa.stocks.repository.StockRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueStockCodeValidator implements ConstraintValidator<UniqueStockCode, String> {

    private StockRepository repository;

    public UniqueStockCodeValidator(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        return repository.findByCode(code) == null;
    }
}
