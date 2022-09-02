package com.wowtown.wowtownbackend.chatroom.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetChatRoomDetailDto {
  private List<GetChatMessageDto> chatMessageList;
  private List<GetParticipantAvatarDto> avatarList;
}
