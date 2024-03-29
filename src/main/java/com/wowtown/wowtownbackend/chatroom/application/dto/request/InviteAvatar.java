package com.wowtown.wowtownbackend.chatroom.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InviteAvatar {
  private long avatarId;

  private String nickName;
}
