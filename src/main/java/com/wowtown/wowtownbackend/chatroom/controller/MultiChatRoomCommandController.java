package com.wowtown.wowtownbackend.chatroom.controller;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.MultiChatRoomCommandExecutor;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.CreateMultiChatRoomDto;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.CreateStudyGroupDto;
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

    @PostMapping(value ="/newMultiChatRoom")
    public ResponseEntity createMultiChatRoom(
            @RequestParam("studyGroupId") Long studyGroupId,
            @RequestBody CreateMultiChatRoomDto dto
           ){
        multiChatRoomCommandExecutor.createMultiChatRoom(studyGroupId,dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

//    @PostMapping(value ="/{multiChatRoomId}/edit")
//    public ResponseEntity updateMultiChatRoom(@PathVariable("multiChatRoomId") long multiChatRoomId){
//
//    }

    @PostMapping(value = "/{multiChatRoomId}")
    public ResponseEntity deleteMultiChatRoom(
            @RequestParam("studyGroupId") Long studyGroupId,
            @PathVariable("multiChatRoomId") Long multiChatRoomId,
            Avatar avatar){
        multiChatRoomCommandExecutor.deleteMultiChatRoom(studyGroupId,multiChatRoomId,avatar);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
