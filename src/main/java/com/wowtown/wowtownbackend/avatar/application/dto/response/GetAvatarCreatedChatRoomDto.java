package com.wowtown.wowtownbackend.avatar.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAvatarCreatedChatRoomDto {
  private UUID chatRoomUUID;
}
