package com.wowtown.wowtownbackend.character.controller;

import com.wowtown.wowtownbackend.character.application.CharacterCommandExecutor;
import com.wowtown.wowtownbackend.character.application.dto.request.CreateOrUpdateCharacterDto;
import com.wowtown.wowtownbackend.common.argumentresolver.LoginUser;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CharacterCommandController {
  private final CharacterCommandExecutor characterCommandExecutor;

  @PostMapping(value = "/characters")
  public ResponseEntity createCharacter(
      @RequestParam("channelId") Long channelId,
      @RequestBody CreateOrUpdateCharacterDto dto,
      @LoginUser User user) {
    characterCommandExecutor.createCharacter(channelId, dto, user);
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  @PutMapping(value = "/characters")
  public ResponseEntity updateCharacter(
      @RequestParam("channelId") Long channelId,
      @RequestBody CreateOrUpdateCharacterDto dto,
      @LoginUser User user) {
    characterCommandExecutor.updateCharacter(channelId, dto, user);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @DeleteMapping(value = "/characters")
  public ResponseEntity deleteCharacter(
      @RequestParam("channelId") Long channelId, @LoginUser User user) {
    characterCommandExecutor.deleteCharacter(channelId, user);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
