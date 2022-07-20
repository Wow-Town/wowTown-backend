package com.wowtown.wowtownbackend.error.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InternalServerError {
  private int code;

  private String message;
}
