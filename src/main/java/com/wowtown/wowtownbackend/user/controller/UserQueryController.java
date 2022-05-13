package com.wowtown.wowtownbackend.user.controller;

import com.wowtown.wowtownbackend.user.application.UserQueryProcessor;
import com.wowtown.wowtownbackend.user.application.dto.request.LoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UserEmailCheckDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserQueryController {

  private final UserQueryProcessor userQueryProcessor;

  @PostMapping(value = "/login")
  public ResponseEntity login(@RequestBody LoginUserDto dto) {
    userQueryProcessor.loginUser(dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping(value = "/signUp/check")
  public ResponseEntity checkUserEmailOverlap(@RequestBody UserEmailCheckDto dto) {
    userQueryProcessor.checkUserEmailOverlap(dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping(value = "/{userId}/channels")
  public ResponseEntity getUserChannel(@PathVariable("userId") long userId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userQueryProcessor.getUserChannelWithUserId(userId));
  }
}
