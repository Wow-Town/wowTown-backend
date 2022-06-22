package com.wowtown.wowtownbackend.avatar.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateAvatarDto {
  private String nickName;

  private String description;

  private List<String> interestList;
}
