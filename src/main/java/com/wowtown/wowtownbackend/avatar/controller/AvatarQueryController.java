package com.wowtown.wowtownbackend.avatar.controller;

import com.wowtown.wowtownbackend.avatar.application.AvatarQueryProcessor;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarFriendDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Validated
@Controller
@RequiredArgsConstructor
public class AvatarQueryController {
  private final AvatarQueryProcessor avatarQueryProcessor;

  @ApiOperation(value = "channelId와 user를 통해 아바타 가져오기", notes = "")
  @GetMapping(value = "/avatars")
  public ResponseEntity getAvatar(
      @ApiIgnore @UserChannel Channel channel,
      @ApiIgnore @LoginUser User user,
      HttpServletRequest request,
      HttpServletResponse response) {

    GetAvatarDto getAvatarDto = avatarQueryProcessor.getAvatar(channel, user);

    String origin = request.getHeader("Origin");

    String domain =
        (origin == null
                || origin.equals("http://localhost:3000")
                || origin.equals("http://localhost:8080"))
            ? "localhost"
            : origin.substring(7);

    Cookie cookie = new Cookie("avatarId", String.valueOf(getAvatarDto.getAvatarId()));
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setDomain(domain);
    response.addCookie(cookie);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(getAvatarDto);
  }

  @ApiOperation(value = "avatarId를 통해 아바타 가져오기", notes = "")
  @GetMapping(value = "/avatars/{avatarId}")
  public ResponseEntity getAvatar(@PathVariable("avatarId") Long avatarId) {

    GetAvatarDto getAvatarDto = avatarQueryProcessor.getAvatar(avatarId);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(getAvatarDto);
  }

  @ApiOperation(value = "아바타 친구목록 가져오기", notes = "")
  @GetMapping(value = "/avatars/friends")
  public ResponseEntity getAvatarFriend(@ApiIgnore @UserAvatar Avatar avatar) {

    List<GetAvatarFriendDto> getAvatarFriendDtoList =
        avatarQueryProcessor.getAvatarFriendList(avatar);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(getAvatarFriendDtoList);
  }
}
