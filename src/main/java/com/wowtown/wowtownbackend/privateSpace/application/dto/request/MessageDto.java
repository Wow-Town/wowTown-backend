package com.wowtown.wowtownbackend.privateSpace.application.dto.request;

import com.wowtown.wowtownbackend.common.event.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
  private MessageType type;

  private UUID privateSpaceUUID;

  private Long senderId;

  private UUID peerUUID;
}
