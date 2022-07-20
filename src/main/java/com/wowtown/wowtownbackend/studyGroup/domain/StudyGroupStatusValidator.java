package com.wowtown.wowtownbackend.studyGroup.domain;

import com.wowtown.wowtownbackend.common.annotation.ValidStudyGroupStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StudyGroupStatusValidator
    implements ConstraintValidator<ValidStudyGroupStatus, String> {
  private ValidStudyGroupStatus annotation;

  @Override
  public void initialize(ValidStudyGroupStatus constraintAnnotation) {
    this.annotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    boolean result = false;

    // enum클래스에 있는 값들 가져오기
    Object[] enumValues = this.annotation.enumClass().getEnumConstants();

    if (enumValues != null) {
      for (Object enumValue : enumValues) {
        if (value.equals(enumValue.toString())) {
          result = true;
          break;
        }
      }
    }

    return result;
  }
}
