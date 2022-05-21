package com.wowtown.wowtownbackend.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetLoginUserDto {
  private Long userId;

  private String email;

  private String userName;
}
