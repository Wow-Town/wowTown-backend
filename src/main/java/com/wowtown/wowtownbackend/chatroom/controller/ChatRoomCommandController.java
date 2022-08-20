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

  // 친구 초대 후 채팅
  @ApiOperation(value = "아바타 채팅방 생성하기", notes = "유저,채널ID,dto 사용")
  @PostMapping(value = "/chatRooms/create/avatar")
  public ResponseEntity createAvatarChatroom(
      @RequestBody InviteAvatar dto, @ApiIgnore @UserAvatar Avatar avatar) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(chatRoomCommandExecutor.createAvatarChatroom(dto, avatar));
  }

  //  // 오픈채팅
  //  @ApiOperation(value = "스터디그룹 채팅방 생성하기", notes = "유저,채널ID,dto 사용")
  //  @PostMapping(value = "/create/studyGroup")
  //  public ResponseEntity createStudyGroupChatroom(@RequestParam("Id") @Min(1) long studyGroupId)
  // {
  //
  //    return ResponseEntity.status(HttpStatus.CREATED)
  //        .contentType(MediaType.APPLICATION_JSON)
  //        .body(chatRoomCommandExecutor.createStudyGroupChatroom(studyGroupId));
  //  }

  //  // 이 아래 두개 필요 없을수도..??
  //  // 입장 퇴장은 프론트에서 하기때문이다. 프론트에서 /sub/chatRoom/{UUID} 로 메시지를 보내면 메시지 타입을 보고 서비스를 이용하여 입장시켜주면 될듯..
  //  // 이 sub에 어떤 아바타가 있는지 어떻게 알지??
  //
  //  @ApiOperation(value = "채팅방 입장하기", notes = "")
  //  @PostMapping(value = "/{uuid}/enter")
  //  public ResponseEntity enterChatRoom(
  //      @PathVariable("uuid") UUID chatRoomUUID, @ApiIgnore @UserAvatar Avatar avatar) {
  //    chatRoomCommandExecutor.enterChatRoom(chatRoomUUID, avatar);
  //    String message = avatar.getNickName() + "님이 입장하셨습니다.";
  //    sendingOperations.convertAndSend("/sub/chatRooms/" + chatRoomUUID.toString(), message);
  //    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  //  }
  //
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
