package com.wowtown.wowtownbackend.avatar.controller;

import com.wowtown.wowtownbackend.avatar.application.AvatarQueryProcessor;
import com.wowtown.wowtownbackend.avatar.application.dto.request.AvatarNickNameCheckDto;
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
public class AvatarQueryController {
  private final AvatarQueryProcessor avatarQueryProcessor;

  @GetMapping(value = "/avatars")
  public ResponseEntity getAvatar(@RequestParam("channelId") Long channelId, @LoginUser User user) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(avatarQueryProcessor.getAvatar(channelId, user));
  }

  @GetMapping(value = "/avatars/check")
  public ResponseEntity checkAvatarNickNameOverlap(
      @RequestParam("channelId") Long channelId, AvatarNickNameCheckDto dto) {
    avatarQueryProcessor.checkAvatarNickNameOverlap(channelId, dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
