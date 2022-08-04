package com.wowtown.wowtownbackend.chatroom.application.dto.response;

import com.wowtown.wowtownbackend.chatroom.domain.ChatRoomType;
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

  private ChatRoomType roomType;
}
