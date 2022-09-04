package com.wowtown.wowtownbackend.error.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Unauthorized {
  private int code;

  private String message;
}
