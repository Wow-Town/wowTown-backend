package com.wowtown.wowtownbackend.privateSpace.controller;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import com.wowtown.wowtownbackend.privateSpace.application.PrivateSpaceQueryProcessor;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

@Controller
@RequestMapping("/privateSpace")
@RequiredArgsConstructor
public class PrivateSpaceQueryController {
  private final PrivateSpaceQueryProcessor privateSpaceQueryProcessor;

  @ApiOperation(value = "아바타가 참여중인 채팅방 조회", notes = "U.")
  @GetMapping
  public ResponseEntity getPrivateSpaces(@ApiIgnore @UserAvatar Avatar avatar) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(privateSpaceQueryProcessor.getPrivateSpaceListByAvatar(avatar));
  }

  @ApiOperation(value = "아바타가 참여중인 채팅방 조회", notes = "U.")
  @GetMapping("/{uuid}")
  public ResponseEntity getPrivateSpace(
      @PathVariable("uuid") UUID privateSpaceUUID, @ApiIgnore @UserAvatar Avatar avatar) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            privateSpaceQueryProcessor.getPrivateSpaceByPrivateSpaceUUIDAndAvatar(
                privateSpaceUUID, avatar));
  }
}
