package com.wowtown.wowtownbackend.error.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotFound {
  private int code;

  private String message;
}
