package com.wowtown.wowtownbackend.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
  @NotEmpty(message = "email 필드는 비어있을 수 없습니다.")
  @Email(message = "이메일 형식이 맞지 않습니다.")
  private String email;

  @NotEmpty(message = "userName 필드는 비어있을 수 없습니다.")
  private String userName;
}
