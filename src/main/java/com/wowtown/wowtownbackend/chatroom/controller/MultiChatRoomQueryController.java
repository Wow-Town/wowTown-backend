package com.wowtown.wowtownbackend.chatroom.controller;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.MultiChatRoomQueryProcessor;
import com.wowtown.wowtownbackend.common.argumentresolver.UserAvatar;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/chatRooms")
@RequiredArgsConstructor
public class MultiChatRoomQueryController {
    private final MultiChatRoomQueryProcessor multiChatRoomQueryProcessor;

    @GetMapping(value = "/multiChatRooms/{multiChatRoomID}")
    public ResponseEntity getMultiChatRoom(
            @PathVariable("multiChatRoomId") Long multiChatRoomId){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(multiChatRoomQueryProcessor.getMultiChatRoom(multiChatRoomId));
    }
    @GetMapping(value = "/multiChatRooms")
    public ResponseEntity getMultiChatRooms(
            @RequestParam("channelId") Long channelId,
            @UserAvatar Avatar avatar){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(multiChatRoomQueryProcessor.getMultiChatRoomsByAvatar(channelId,avatar));
    }

}
