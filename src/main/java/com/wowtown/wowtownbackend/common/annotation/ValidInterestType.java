package com.wowtown.wowtownbackend.common.annotation;

import com.wowtown.wowtownbackend.common.domain.InterestTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = InterestTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInterestType {
  String message() default "Invalid value. This is not permitted.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<? extends java.lang.Enum<?>> enumClass();
}
