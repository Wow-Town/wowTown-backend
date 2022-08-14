package com.wowtown.wowtownbackend.chatroom.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.stomp.StompCommand;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
  private StompCommand type;

  private String sessionId;

  private UUID chatRoomUUID;

  private String sender;

  private Long senderId;

  private String message;
}
