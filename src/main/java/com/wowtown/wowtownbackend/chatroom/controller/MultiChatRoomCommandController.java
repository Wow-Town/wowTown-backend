package com.wowtown.wowtownbackend.chatroom.controller;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.MultiChatRoomCommandExecutor;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.CreateOrUpdateMultiChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.EnterOrExitMultiChatRoomDto;
import com.wowtown.wowtownbackend.common.argumentresolver.UserAvatar;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chatRooms/multiChatRooms")
@RequiredArgsConstructor
public class MultiChatRoomCommandController {
    private final MultiChatRoomCommandExecutor multiChatRoomCommandExecutor;

//    @PostMapping(value ="/newMultiChatRoom")
//    public ResponseEntity createMultiChatRoom(
//            @PathVariable("studyGroupId") Long studyGroupId,
//            @RequestBody CreateOrUpdateMultiChatRoomDto dto,
//            @UserAvatar Avatar avatar
//           ){
//        multiChatRoomCommandExecutor.createMultiChatRoom(studyGroupId,dto,avatar);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .contentType(MediaType.APPLICATION_JSON)
//                .build();
//    }
    //생성은 스터디그룹이 생성될 떄 자대로

    @PostMapping(value ="/{multiChatRoomId}/edit")
    public ResponseEntity updateMultiChatRoom(
            @PathVariable("multiChatRoomId") long multiChatRoomId,
            @RequestBody CreateOrUpdateMultiChatRoomDto dto,
            @UserAvatar Avatar avatar){
        multiChatRoomCommandExecutor.updateMultiChatRoom(dto,multiChatRoomId,avatar);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

//    @PostMapping(value = "/{multiChatRoomId}")
//    public ResponseEntity deleteMultiChatRoom(
//            @PathVariable("multiChatRoomId") Long multiChatRoomId,
//            @UserAvatar Avatar avatar){
//        multiChatRoomCommandExecutor.deleteMultiChatRoom(multiChatRoomId,avatar);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON)
//                .build();
//    }

    @PostMapping(value = "/{multiChatRoomId}/enter")
    public ResponseEntity enterMultiChatRoom(
            @PathVariable("multiChatRoomId") long multiChatRoomId,
            @RequestBody EnterOrExitMultiChatRoomDto dto,
            @UserAvatar Avatar avatar){
        multiChatRoomCommandExecutor.enterMultiChatRoom(multiChatRoomId,dto,avatar);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
    @PostMapping(value = "/{multiChatRoomId}/exit")
    public ResponseEntity exitMultiChatRoom(
            @PathVariable("multiChatRoomId") long multiChatRoomId,
            @RequestBody EnterOrExitMultiChatRoomDto dto,
            @UserAvatar Avatar avatar){
        multiChatRoomCommandExecutor.enterMultiChatRoom(multiChatRoomId,dto,avatar);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
