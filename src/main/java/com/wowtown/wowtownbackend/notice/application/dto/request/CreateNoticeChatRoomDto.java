package com.wowtown.wowtownbackend.notice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoticeChatRoomDto {
  private String roomName;

  private int personnel;
}
