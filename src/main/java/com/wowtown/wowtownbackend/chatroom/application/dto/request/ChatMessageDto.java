package com.wowtown.wowtownbackend.chatroom.application.dto.request;

import com.wowtown.wowtownbackend.common.event.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
  private MessageType type;

  private UUID chatRoomUUID;

  private String sender;

  private Long senderId;

  private byte[] message;
}
