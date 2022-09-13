package com.wowtown.wowtownbackend.avatar.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendAvatarDto {
  @NotNull(message = "friendId 필드는 비어있을 수 없습니다.")
  private long friendAvatarId;
}
