package com.wowtown.wowtownbackend.user.controller;

import com.wowtown.wowtownbackend.common.annotation.LoginUser;
import com.wowtown.wowtownbackend.user.application.UserQueryProcessor;
import com.wowtown.wowtownbackend.user.application.dto.request.LoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UserEmailCheckDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetAccessToken;
import com.wowtown.wowtownbackend.user.application.dto.response.GetJwtTokenDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetLoginUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Log4j2
@Validated
@Controller
@RequiredArgsConstructor
public class UserQueryController {

  private final UserQueryProcessor userQueryProcessor;

  @ApiOperation(value = "로그인", notes = "")
  @PostMapping(value = "/login")
  public ResponseEntity login(
      @Valid @RequestBody LoginUserDto dto,
      HttpServletRequest request,
      HttpServletResponse response) {

    GetJwtTokenDto getJwtTokenDto = userQueryProcessor.login(dto);

    String origin = request.getHeader("Origin");

    String domain =
        (origin == null
                || origin.equals("http://localhost:3000")
                || origin.equals("http://localhost:8080"))
            ? "localhost"
            : origin.substring(7);

    Cookie cookie = new Cookie("api-key", getJwtTokenDto.getRefreshToken());
    cookie.setMaxAge(60 * 60 * 24 * 30);
    cookie.setPath("/");
    cookie.setDomain(domain);
    cookie.setHttpOnly(true);

    response.addCookie(cookie);

    GetAccessToken getAccessToken = new GetAccessToken(getJwtTokenDto.getAccessToken());

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(getAccessToken);
  }

  @ApiOperation(value = "이메일 중복 체크", notes = "이메일이 중복되는지 확인합니다")
  @PostMapping(value = "/signUp/check")
  public ResponseEntity checkUserEmailOverlap(@Valid @RequestBody UserEmailCheckDto dto) {
    userQueryProcessor.checkUserEmailOverlap(dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "로그인유저 조회", notes = "", response = GetLoginUserDto.class)
  @GetMapping(value = "/users/self")
  public ResponseEntity<GetLoginUserDto> getLoginUser(@ApiIgnore @LoginUser User user) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userQueryProcessor.getLoginUser(user));
  }

  @ApiOperation(value = "사용자 참여중인 채널 조회", notes = "UserId를 이용하여 사용자가 참여중인 채널들을 조회합니다.")
  @GetMapping(value = "/users/{userId}/channels")
  public ResponseEntity getUserChannel(@Min(1) @PathVariable("userId") long userId) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userQueryProcessor.getUserChannelWithUserId(userId));
  }
}
