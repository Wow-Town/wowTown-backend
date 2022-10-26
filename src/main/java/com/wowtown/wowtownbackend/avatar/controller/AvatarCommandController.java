package com.wowtown.wowtownbackend.avatar.controller;

import com.wowtown.wowtownbackend.avatar.application.AvatarCommandExecutor;
import com.wowtown.wowtownbackend.avatar.application.dto.request.CreateOrUpdateAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.request.FriendAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.annotation.LoginUser;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import com.wowtown.wowtownbackend.common.annotation.UserChannel;
import com.wowtown.wowtownbackend.user.domain.User;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Validated
@Controller
@RequiredArgsConstructor
public class AvatarCommandController {
  private final AvatarCommandExecutor avatarCommandExecutor;

  @ApiOperation(value = "아바타 생성하기", notes = "유저,채널ID,dto 사용")
  @PostMapping(value = "/avatars")
  public ResponseEntity createAvatar(
      @Valid @RequestBody CreateOrUpdateAvatarDto dto,
      @ApiIgnore @UserChannel Channel channel,
      @ApiIgnore @LoginUser User user,
      HttpServletRequest request,
      HttpServletResponse response) {
    long avatarId = avatarCommandExecutor.createAvatar(dto, channel, user);

    String origin = request.getHeader("Origin");

    String domain =
        (origin == null
                || origin.equals("http://localhost:3000")
                || origin.equals("https://localhost:3000")
                || origin.equals("https://localhost:443"))
            ? "localhost"
            : origin.substring(8);

    ResponseCookie cookie =
        ResponseCookie.from("avatarId", String.valueOf(avatarId))
            .path("/")
            .httpOnly(true)
            .domain(domain)
            .build();
    response.addHeader("Set-Cookie", cookie.toString());

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  @ApiOperation(value = "아바타 수정하기", notes = "유저,채널ID 사용")
  @PutMapping(value = "/avatars")
  public ResponseEntity updateAvatar(
      @Valid @RequestBody CreateOrUpdateAvatarDto dto,
      @ApiIgnore @UserChannel Channel channel,
      @ApiIgnore @LoginUser User user) {
    avatarCommandExecutor.updateAvatar(dto, channel, user);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "아바타 삭제하기", notes = "유저,채널ID 사용")
  @DeleteMapping(value = "/avatars")
  public ResponseEntity deleteAvatar(
      @ApiIgnore @UserChannel Channel channel,
      @ApiIgnore @LoginUser User user,
      HttpServletResponse response) {
    avatarCommandExecutor.deleteAvatar(channel, user);

    // 아바타를 삭제하면 채널에서도 나가야한다.
    Cookie channelCookie = new Cookie("channelId", null);
    channelCookie.setMaxAge(0);
    response.addCookie(channelCookie);

    Cookie avatarCookie = new Cookie("avatarId", null);
    avatarCookie.setMaxAge(0);
    response.addCookie(avatarCookie);

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "친구 신청", notes = "")
  @PostMapping(value = "/avatars/friends/add")
  public ResponseEntity addFriend(
      @Valid @RequestBody FriendAvatarDto dto, @ApiIgnore @UserAvatar Avatar avatar) {

    avatarCommandExecutor.addFriend(dto, avatar);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }
  // 친구 받아주기
  @ApiOperation(value = "친구 신청 승락", notes = "")
  @PostMapping(value = "/avatars/friends/approve")
  public ResponseEntity approveFriendRequest(
      @Valid @RequestBody FriendAvatarDto dto, @ApiIgnore @UserAvatar Avatar avatar) {
    avatarCommandExecutor.approveFriendRequest(dto, avatar);

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  // 친구 거절하기
  @ApiOperation(value = "친구 신청 거절", notes = "")
  @PostMapping(value = "/avatars/friends/reject")
  public ResponseEntity rejectFriendRequest(
      @Valid @RequestBody FriendAvatarDto dto, @ApiIgnore @UserAvatar Avatar avatar) {
    avatarCommandExecutor.rejectFriendRequest(dto, avatar);

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "아바타 게임 입장코드 메일 전송", notes = "유저,채널ID 사용")
  @PostMapping(value = "/avatars/sendEmail")
  public ResponseEntity sendEmail(
      @ApiIgnore @UserChannel Channel channel, @ApiIgnore @LoginUser User user) {
    avatarCommandExecutor.sendEmail(channel, user);

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
