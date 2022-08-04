package com.wowtown.wowtownbackend.chatroom.application.dto.request;

import com.wowtown.wowtownbackend.chatroom.domain.MessageType;
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

  private long sendAvatarId;

  private long receiveAvatarId;

  private String message;
}
