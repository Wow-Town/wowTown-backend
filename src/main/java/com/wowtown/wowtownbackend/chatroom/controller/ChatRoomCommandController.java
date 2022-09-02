package com.wowtown.wowtownbackend.chatroom.controller;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.ChatRoomCommandExecutor;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.ChatMessageDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.InviteAvatar;
import com.wowtown.wowtownbackend.chatroom.domain.MessageType;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequiredArgsConstructor
public class ChatRoomCommandController {
  private final AvatarProvider avatarProvider;
  private final ChatRoomCommandExecutor chatRoomCommandExecutor;
  private final SimpMessageSendingOperations sendingOperations;

  // 아바타 채팅방 생성 (공고 채팅방은 공고 생성시 자동으로 만들어줌)
  @ApiOperation(value = "아바타 채팅방 생성하기", notes = "유저,채널ID,dto 사용")
  @PostMapping(value = "/chatRooms/create/avatar")
  public ResponseEntity createAvatarChatroom(
      @RequestBody InviteAvatar dto, @ApiIgnore @UserAvatar Avatar avatar) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(chatRoomCommandExecutor.createAvatarChatroom(dto, avatar));
  }

  // 채팅방 완전히 나가기 나중에 필요한 기능
  //  @ApiOperation(value = "채팅방 나가기", notes = ".")
  //  @PostMapping(value = "/{uuid}/leave")
  //  public ResponseEntity leaveChatRoom(
  //      @PathVariable("uuid") UUID chatRoomUUID, @ApiIgnore @UserAvatar Avatar avatar) {
  //    chatRoomCommandExecutor.leaveChatRoom(chatRoomUUID, avatar);
  //    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  //  }

  @MessageMapping("/chatRooms/message")
  public void message(@RequestBody ChatMessageDto message) {
    if (message.getType() == MessageType.ENTER) {
      chatRoomCommandExecutor.enterChatRoom(message);
    } else if (message.getType() == MessageType.LEAVE) {
      chatRoomCommandExecutor.leaveChatRoom(message);
    } else {
      chatRoomCommandExecutor.sendChatMessage(message);
    }
  }
}
