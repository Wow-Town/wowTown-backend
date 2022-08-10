package com.wowtown.wowtownbackend.avatar.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAvatarChatRoomDto {
  private String opponentAvatarNickName;
}
