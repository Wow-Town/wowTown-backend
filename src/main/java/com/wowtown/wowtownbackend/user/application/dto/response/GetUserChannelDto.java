package com.wowtown.wowtownbackend.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserChannelDto {
  private Long channelId;

  private String channelName;
}
