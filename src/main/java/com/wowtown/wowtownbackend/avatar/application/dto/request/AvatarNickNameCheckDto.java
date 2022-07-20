package com.wowtown.wowtownbackend.avatar.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvatarNickNameCheckDto {
  @NotEmpty(message = "nickName 필드는 비어있을 수 없습니다.")
  private String nickName;
}
