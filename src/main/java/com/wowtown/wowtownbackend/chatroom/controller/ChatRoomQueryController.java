// package com.wowtown.wowtownbackend.chatroom.controller;
//
// import com.wowtown.wowtownbackend.avatar.domain.Avatar;
// import com.wowtown.wowtownbackend.chatroom.application.ChatRoomQueryProcessor;
// import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
// import io.swagger.annotations.ApiOperation;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import springfox.documentation.annotations.ApiIgnore;
//
// import java.util.UUID;
//
// @Controller
// @RequestMapping("/chatRooms")
// @RequiredArgsConstructor
// public class ChatRoomQueryController {
//  private final ChatRoomQueryProcessor chatRoomQueryProcessor;
//
//  @ApiOperation(value = "채팅방 조회", notes = "U.")
//  @GetMapping(value = "/{uuid}")
//  public ResponseEntity getChatRoom(@PathVariable("uuid") UUID chatRoomUUID) {
//    return ResponseEntity.status(HttpStatus.OK)
//        .contentType(MediaType.APPLICATION_JSON)
//        .body(chatRoomQueryProcessor.getChatRoom(chatRoomUUID));
//  }
//
//  @ApiOperation(value = "아바타가 참여중인 채팅방 조회", notes = "U.")
//  @GetMapping
//  public ResponseEntity getChatRooms(@ApiIgnore @UserAvatar Avatar avatar) {
//    return ResponseEntity.status(HttpStatus.OK)
//        .contentType(MediaType.APPLICATION_JSON)
//        .body(chatRoomQueryProcessor.getChatRoomListByAvatar(avatar));
//  }
// }
