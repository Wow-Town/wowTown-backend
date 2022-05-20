package com.wowtown.wowtownbackend.channel.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetChannelDto {
  private String channelName;

  private int maxJoinNum;

  private int currentJoinNum;
}
