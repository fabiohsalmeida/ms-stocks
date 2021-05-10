package com.fhsa.stocks.validator;

import com.fhsa.stocks.annotation.UniqueStockName;
import com.fhsa.stocks.repository.StockRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueStockNameValidator implements ConstraintValidator<UniqueStockName, String> {

    private StockRepository repository;

    public UniqueStockNameValidator(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return repository.findByName(name) == null;
    }
}
