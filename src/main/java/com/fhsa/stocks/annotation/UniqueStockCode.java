package com.fhsa.stocks.annotation;

import com.fhsa.stocks.validator.UniqueStockCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueStockCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueStockCode {

    String message() default "Non-unique code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
