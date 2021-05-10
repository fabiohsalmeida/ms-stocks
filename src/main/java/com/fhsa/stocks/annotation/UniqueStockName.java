package com.fhsa.stocks.annotation;

import com.fhsa.stocks.validator.UniqueStockNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueStockNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueStockName {

    String message() default "Non-unique stock name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
