package com.wowtown.wowtownbackend.user.controller;

import com.wowtown.wowtownbackend.user.application.UserQueryProcessor;
import com.wowtown.wowtownbackend.user.application.dto.request.LoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UserEmailCheckDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserQueryController {

  private final UserQueryProcessor userQueryProcessor;

  @PostMapping(value = "/login")
  public ResponseEntity<GetUserDto> login(@RequestBody LoginUserDto dto) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userQueryProcessor.loginUser(dto));
  }

  @PostMapping(value = "/signUp/check")
  public ResponseEntity checkUserEmailOverlap(@RequestBody UserEmailCheckDto dto) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userQueryProcessor.checkUserEmailOverlap(dto));
  }

  @GetMapping(value = "/{userId}/channels")
  public ResponseEntity getUserChannel(@PathVariable("userId") long userId) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userQueryProcessor.getUserChannelWithUserId(userId));
  }
}
