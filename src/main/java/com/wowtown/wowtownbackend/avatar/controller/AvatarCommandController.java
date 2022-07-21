package com.wowtown.wowtownbackend.avatar.controller;

import com.wowtown.wowtownbackend.avatar.application.AvatarCommandExecutor;
import com.wowtown.wowtownbackend.avatar.application.dto.request.CreateOrUpdateAvatarDto;
import com.wowtown.wowtownbackend.common.annotation.LoginUser;
import com.wowtown.wowtownbackend.user.domain.User;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@Controller
@RequiredArgsConstructor
public class AvatarCommandController {
  private final AvatarCommandExecutor avatarCommandExecutor;


  @ApiOperation(value = "아바타 생성하기", notes = "유저,채널ID,dto 사용")
  @PostMapping(value = "/avatars")
  public ResponseEntity createAvatar(
      @RequestParam("channelId") @Min(1) long channelId,
      @Valid @RequestBody CreateOrUpdateAvatarDto dto,
      @ApiIgnore @LoginUser User user,
      HttpServletResponse response) {
    long avatarId = avatarCommandExecutor.createAvatar(channelId, dto, user);

    Cookie cookie = new Cookie("avatarId", String.valueOf(avatarId));
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setDomain("wowtown.co.kr");
    response.addCookie(cookie);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  @ApiOperation(value = "아바타 수정하기", notes = "유저,채널ID 사용")
  @PutMapping(value = "/avatars")
  public ResponseEntity updateAvatar(
      @RequestParam("channelId") @Min(1) long channelId,
      @Valid @RequestBody CreateOrUpdateAvatarDto dto,
      @ApiIgnore @LoginUser User user) {
    avatarCommandExecutor.updateAvatar(channelId, dto, user);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "아바타 삭제하기", notes = "유저,채널ID 사용")
  @DeleteMapping(value = "/avatars")
  public ResponseEntity deleteAvatar(
      @RequestParam("channelId") @Min(1) long channelId,
      @ApiIgnore  @LoginUser User user,
      HttpServletResponse response) {
    avatarCommandExecutor.deleteAvatar(channelId, user);

    Cookie cookie = new Cookie("avatarId", null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
