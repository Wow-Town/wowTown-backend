package com.wowtown.wowtownbackend.user.controller;

import com.wowtown.wowtownbackend.user.application.UserCommandExecutor;
import com.wowtown.wowtownbackend.user.application.dto.request.ChangeUserPWDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UpdateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCommandController {

  private final UserCommandExecutor userCommandExecutor;

  @PostMapping(value = "/signUp")
  public ResponseEntity signUp(@RequestBody CreateUserDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userCommandExecutor.createUser(dto));
  }

  @PutMapping(value = "/{userId}/edit")
  public ResponseEntity updateUser(
      @PathVariable("userId") long userId, @RequestBody UpdateUserDto dto) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userCommandExecutor.updateUser(userId, dto));
  }

  @PutMapping(value = "/{userId}/edit/password")
  public ResponseEntity updateUserPW(
      @PathVariable("userId") long userId, @RequestBody ChangeUserPWDto dto) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userCommandExecutor.updateUserPW(userId, dto));
  }

  @DeleteMapping(value = "/{userId}")
  public ResponseEntity deleteUser(@PathVariable("userId") long userId) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userCommandExecutor.deleteUser(userId));
  }

  @PostMapping(value = "/{userId}/channels")
  public ResponseEntity selectUserChannel(
      @PathVariable("userId") long userId, CreateUserChannelDto dto) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userCommandExecutor.addUserChannel(userId, dto));
  }
}
