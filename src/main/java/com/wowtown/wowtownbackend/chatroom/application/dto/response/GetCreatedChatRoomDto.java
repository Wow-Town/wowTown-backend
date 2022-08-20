package com.wowtown.wowtownbackend.chatroom.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCreatedChatRoomDto {
  private UUID chatRoomUUID;

  private String roomName;
}
