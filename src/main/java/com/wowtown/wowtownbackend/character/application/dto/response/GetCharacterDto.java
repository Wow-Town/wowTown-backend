package com.wowtown.wowtownbackend.character.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCharacterDto {
  private Long characterId;

  private String nickName;

  private String description;
}
