package com.wowtown.wowtownbackend.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
  private Long userId;

  private String email;

  private String userName;
}
