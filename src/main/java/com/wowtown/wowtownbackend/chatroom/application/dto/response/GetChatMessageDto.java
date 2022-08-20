package com.wowtown.wowtownbackend.chatroom.application.dto.response;

import com.wowtown.wowtownbackend.chatroom.domain.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetChatMessageDto {
  private MessageType type;

  private String sender;

  private String message;

  private int count; // 읽지 않은 사용자 수

  private LocalDateTime sendAt;
}
