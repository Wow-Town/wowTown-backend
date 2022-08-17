package com.wowtown.wowtownbackend.notice.domain;

import com.wowtown.wowtownbackend.common.annotation.ValidNoticeStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoticeStatusValidator
    implements ConstraintValidator<ValidNoticeStatus, String> {
  private ValidNoticeStatus annotation;

  @Override
  public void initialize(ValidNoticeStatus constraintAnnotation) {
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
