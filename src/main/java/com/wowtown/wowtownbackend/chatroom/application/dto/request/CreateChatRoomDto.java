package com.wowtown.wowtownbackend.chatroom.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomDto {
  private List<InviteAvatar> inviteAvatarList;
}
