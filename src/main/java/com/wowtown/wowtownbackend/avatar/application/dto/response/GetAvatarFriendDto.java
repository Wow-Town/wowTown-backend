package com.wowtown.wowtownbackend.avatar.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAvatarFriendDto {
  private Long friendId;
  private String friendNickName;
  private String status;
}
