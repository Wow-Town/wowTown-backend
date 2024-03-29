package com.wowtown.wowtownbackend.avatar.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetOtherAvatarDto {
  private Long avatarId;

  private String nickName;

  private String description;

  private Set<String> interests;

  private String friendStatus;
}
