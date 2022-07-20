package com.wowtown.wowtownbackend.error.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDto<T> {
  private T error;
}
