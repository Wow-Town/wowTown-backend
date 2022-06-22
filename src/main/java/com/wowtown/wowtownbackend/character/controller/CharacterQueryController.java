package com.wowtown.wowtownbackend.character.controller;

import com.wowtown.wowtownbackend.character.application.CharacterQueryProcessor;
import com.wowtown.wowtownbackend.character.application.dto.request.CharacterNickNameCheckDto;
import com.wowtown.wowtownbackend.common.argumentresolver.LoginUser;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CharacterQueryController {
  private final CharacterQueryProcessor characterQueryProcessor;

  @GetMapping(value = "/characters")
  public ResponseEntity getCharacter(
      @RequestParam("channelId") Long channelId, @LoginUser User user) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(characterQueryProcessor.getCharacter(channelId, user));
  }

  @GetMapping(value = "/characters/check")
  public ResponseEntity checkCharacterNickNameOverlap(
      @RequestParam("channelId") Long channelId, CharacterNickNameCheckDto dto) {
    characterQueryProcessor.checkCharacterNickNameOverlap(channelId, dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
