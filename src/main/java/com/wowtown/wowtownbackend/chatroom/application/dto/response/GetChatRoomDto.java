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
public class GetChatRoomDto {
  private UUID chatRoomUUID;

  private String roomName;

  private String latestMessage;

  private int receiveMessageNum;

  private String roomType;
}
