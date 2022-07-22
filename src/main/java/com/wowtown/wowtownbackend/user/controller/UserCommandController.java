package com.wowtown.wowtownbackend.user.controller;

import com.wowtown.wowtownbackend.user.application.UserCommandExecutor;
import com.wowtown.wowtownbackend.user.application.dto.request.ChangeUserPWDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UpdateUserDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@Controller
@RequiredArgsConstructor
public class UserCommandController {

  private final UserCommandExecutor userCommandExecutor;
  @ApiOperation(value = "회원가입", notes = ".")
  @PostMapping(value = "/signUp")
  public ResponseEntity signUp(@Valid @RequestBody CreateUserDto dto) {
    userCommandExecutor.createUser(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  @ApiOperation(value = "유저 정보 수정", notes = "UserId를 이용하여 유저 정보를 수정합니다.")
  @PutMapping(value = "/users/{userId}/edit")
  public ResponseEntity updateUser(
      @Min(1) @PathVariable("userId") long userId, @Valid @RequestBody UpdateUserDto dto) {
    userCommandExecutor.updateUser(userId, dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "유저 패스워드 수정", notes = "UserId를 이용하여 유저 패스워드를 수정합니다.")
  @PutMapping(value = "/users/{userId}/edit/password")
  public ResponseEntity updateUserPW(
      @Min(1) @PathVariable("userId") long userId, @Valid @RequestBody ChangeUserPWDto dto) {
    userCommandExecutor.updateUserPW(userId, dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "유저 삭제", notes = "UserId를 이용하여 유저를 삭제합니다.")
  @DeleteMapping(value = "/users/{userId}")
  public ResponseEntity deleteUser(@Min(1) @PathVariable("userId") long userId) {
    userCommandExecutor.deleteUser(userId);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "채널 선택", notes = "")
  @PostMapping(value = "/users/{userId}/channels")
  public ResponseEntity selectUserChannel(
      @PathVariable("userId") @Min(1) long userId, @Valid @RequestBody CreateUserChannelDto dto) {
    userCommandExecutor.addUserChannel(userId, dto);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
