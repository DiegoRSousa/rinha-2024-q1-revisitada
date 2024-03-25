package com.diego.rinha;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = {IsIntegerValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsInteger {

    String message() default "não é inteiro";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
