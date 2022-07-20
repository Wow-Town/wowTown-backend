package com.wowtown.wowtownbackend.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserChannelDto {
  @Min(1)
  private long channelId;
}
