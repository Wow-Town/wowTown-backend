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
@RequiredArgsConstructor
public class UserCommandController {

  private final UserCommandExecutor userCommandExecutor;

  @PostMapping(value = "/signUp")
  public ResponseEntity signUp(@RequestBody CreateUserDto dto) {
    userCommandExecutor.createUser(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  @PutMapping(value = "/users/{userId}/edit")
  public ResponseEntity updateUser(
      @PathVariable("userId") long userId, @RequestBody UpdateUserDto dto) {
    userCommandExecutor.updateUser(userId, dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @PutMapping(value = "/users/{userId}/edit/password")
  public ResponseEntity updateUserPW(
      @PathVariable("userId") long userId, @RequestBody ChangeUserPWDto dto) {
    userCommandExecutor.updateUserPW(userId, dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @DeleteMapping(value = "/users/{userId}")
  public ResponseEntity deleteUser(@PathVariable("userId") long userId) {
    userCommandExecutor.deleteUser(userId);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @PostMapping(value = "/users/{userId}/channels")
  public ResponseEntity selectUserChannel(
      @PathVariable("userId") long userId, CreateUserChannelDto dto) {
    userCommandExecutor.addUserChannel(userId, dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
