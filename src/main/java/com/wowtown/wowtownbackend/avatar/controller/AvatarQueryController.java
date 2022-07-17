package com.wowtown.wowtownbackend.avatar.controller;

import com.wowtown.wowtownbackend.avatar.application.AvatarQueryProcessor;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.common.argumentresolver.LoginUser;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AvatarQueryController {
  private final AvatarQueryProcessor avatarQueryProcessor;

  @GetMapping(value = "/avatars")
  public ResponseEntity getAvatar(
      @RequestParam("channelId") Long channelId,
      @LoginUser User user,
      HttpServletResponse response) {

    GetAvatarDto getAvatarDto = avatarQueryProcessor.getAvatar(channelId, user);

    Cookie cookie = new Cookie("avatarId", String.valueOf(getAvatarDto.getAvatarId()));
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setDomain("wowtown.co.kr");
    response.addCookie(cookie);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(getAvatarDto);
  }

  //  @GetMapping(value = "/avatars")
  //  public ResponseEntity getAvatar(@RequestParam("studyGroupId") Long studyGroupId) {
  //
  //    return ResponseEntity.status(HttpStatus.OK)
  //        .contentType(MediaType.APPLICATION_JSON)
  //        .body(avatarQueryProcessor.getAvatarWithStudyGroupId(studyGroupId));
  //  }
}
