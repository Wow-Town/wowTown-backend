package com.wowtown.wowtownbackend.avatar.controller;

import com.wowtown.wowtownbackend.avatar.application.AvatarCommandExecutor;
import com.wowtown.wowtownbackend.avatar.application.dto.request.CreateOrUpdateAvatarDto;
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
public class AvatarCommandController {
  private final AvatarCommandExecutor avatarCommandExecutor;

  @PostMapping(value = "/avatars")
  public ResponseEntity createAvatar(
      @RequestParam("channelId") Long channelId,
      @RequestBody CreateOrUpdateAvatarDto dto,
      @LoginUser User user) {
    avatarCommandExecutor.createAvatar(channelId, dto, user);
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  @PutMapping(value = "/avatars")
  public ResponseEntity updateAvatar(
      @RequestParam("channelId") Long channelId,
      @RequestBody CreateOrUpdateAvatarDto dto,
      @LoginUser User user) {
    avatarCommandExecutor.updateAvatar(channelId, dto, user);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @DeleteMapping(value = "/avatars")
  public ResponseEntity deleteAvatar(
      @RequestParam("channelId") Long channelId, @LoginUser User user) {
    avatarCommandExecutor.deleteAvatar(channelId, user);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
