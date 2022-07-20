package com.wowtown.wowtownbackend.error.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BadRequest {
  private int code;

  private String field;

  private List<String> message;
}
