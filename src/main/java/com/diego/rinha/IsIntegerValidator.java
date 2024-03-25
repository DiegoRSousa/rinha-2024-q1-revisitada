package com.diego.rinha;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsIntegerValidator implements ConstraintValidator<IsInteger, Object>{

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return (double) value % 1 == 0;
    }
    
}
