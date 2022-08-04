package com.wowtown.wowtownbackend.chatroom.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetChatRoomDetailDto {
  private UUID chatRoomUuid;
  private Integer personnel;
  private Integer participantsNum;
  private List<GetChatRoomAvatarDto> avatarList;
}
