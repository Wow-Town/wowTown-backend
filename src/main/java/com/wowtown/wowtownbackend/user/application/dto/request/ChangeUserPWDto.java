package com.wowtown.wowtownbackend.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserPWDto {
  @NotEmpty(message = "currentPW 필드는 비어있을 수 없습니다.")
  @Pattern(
      message = "비밀번호 규칙에 맞지 않습니다.",
      regexp = "(?=.*[a-z])(?=.*\\d)(?=.*[!@,.?])[a-zA-Z\\d!@,.?]{8,15}")
  private String currentPW;

  @NotEmpty(message = "newPW 필드는 비어있을 수 없습니다.")
  @Pattern(
      message = "비밀번호 규칙에 맞지 않습니다.",
      regexp = "(?=.*[a-z])(?=.*\\d)(?=.*[!@,.?])[a-zA-Z\\d!@,.?]{8,15}")
  private String newPW;
}
