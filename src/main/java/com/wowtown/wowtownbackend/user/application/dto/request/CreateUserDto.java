package com.wowtown.wowtownbackend.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
  @NotEmpty(message = "email 필드는 비어있을 수 없습니다.")
  @Email(message = "이메일 형식이 맞지 않습니다.")
  private String email;

  @NotEmpty(message = "userName 필드는 비어있을 수 없습니다.")
  private String userName;

  @NotEmpty(message = "password 필드는 비어있을 수 없습니다.")
  @Pattern(
      message = "비밀번호 규칙에 맞지 않습니다.",
      regexp = "(?=.*[a-z])(?=.*\\d)(?=.*[!@,.?])[a-zA-Z\\d!@,.?]{8,15}")
  private String password;
}
