package com.wowtown.wowtownbackend.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
  private String email;

  private String userName;

  private String password;
}
