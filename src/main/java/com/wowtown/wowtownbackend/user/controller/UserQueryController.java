package com.wowtown.wowtownbackend.user.controller;

import com.wowtown.wowtownbackend.common.annotation.LoginUser;
import com.wowtown.wowtownbackend.user.application.UserQueryProcessor;
import com.wowtown.wowtownbackend.user.application.dto.request.LoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UserEmailCheckDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetAccessToken;
import com.wowtown.wowtownbackend.user.application.dto.response.GetJwtTokenDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetLoginUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@Controller
@RequiredArgsConstructor
public class UserQueryController {

  private final UserQueryProcessor userQueryProcessor;

  @PostMapping(value = "/login")
  public ResponseEntity login(@Valid @RequestBody LoginUserDto dto, HttpServletResponse response) {

    GetJwtTokenDto getJwtTokenDto = userQueryProcessor.login(dto);

    Cookie cookie = new Cookie("api-key", getJwtTokenDto.getRefreshToken());
    cookie.setMaxAge(60 * 60 * 24 * 30);
    cookie.setPath("/");
    cookie.setDomain("wowtown.co.kr");
    cookie.setHttpOnly(true);

    response.addCookie(cookie);

    GetAccessToken getAccessToken = new GetAccessToken(getJwtTokenDto.getAccessToken());

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(getAccessToken);
  }

  @PostMapping(value = "/signUp/check")
  public ResponseEntity checkUserEmailOverlap(@Valid @RequestBody UserEmailCheckDto dto) {
    userQueryProcessor.checkUserEmailOverlap(dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @GetMapping(value = "/users/self")
  public ResponseEntity<GetLoginUserDto> getLoginUser(@LoginUser User user) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userQueryProcessor.getLoginUser(user));
  }

  @GetMapping(value = "/users/{userId}/channels")
  public ResponseEntity getUserChannel(@Min(1) @PathVariable("userId") long userId) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userQueryProcessor.getUserChannelWithUserId(userId));
  }
}
