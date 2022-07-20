package com.wowtown.wowtownbackend.common.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Embeddable
public class Interest {
  @Enumerated(EnumType.STRING)
  private InterestType type;

  protected Interest() {}

  public Interest(InterestType type) {
    this.type = type;
  }
}
