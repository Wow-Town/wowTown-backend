package com.wowtown.wowtownbackend.common.domain;

import com.wowtown.wowtownbackend.common.annotation.ValidInterestType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class InterestTypeValidator implements ConstraintValidator<ValidInterestType, List<String>> {
  private ValidInterestType annotation;

  @Override
  public void initialize(ValidInterestType constraintAnnotation) {
    this.annotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(List<String> values, ConstraintValidatorContext context) {
    boolean result = false;
    // enum클래스에 있는 값들 가져오기
    Object[] enumValues = this.annotation.enumClass().getEnumConstants();

    if (enumValues != null) {
      // value값을 가져와 enum 클래스에 있는 값과 하나씩 비교한다.
      for (String value : values) {
        if (value.equals("")) {
          result = false;
          break;
        }
        for (Object enumValue : enumValues) {
          if (value.equals(enumValue.toString())) {
            result = true;
            break;
          }
        }
      }
    }
    return result;
  }
}
