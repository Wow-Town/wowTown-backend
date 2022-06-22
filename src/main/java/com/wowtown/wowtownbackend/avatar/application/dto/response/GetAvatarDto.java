package com.wowtown.wowtownbackend.avatar.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAvatarDto {
  private Long avatarId;

  private String nickName;

  private String description;
}
