package com.wowtown.wowtownbackend.chatroom.controller;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.MultiChatRoomQueryProcessor;

import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/chatRooms")
@RequiredArgsConstructor
public class MultiChatRoomQueryController {
    private final MultiChatRoomQueryProcessor multiChatRoomQueryProcessor;

    @ApiOperation(value = "multiRoomId로 멀티룸 조회", notes = "당장은 필요없음")
    @GetMapping(value = "/multiChatRooms/{multiChatRoomID}")
    public ResponseEntity getMultiChatRoom(
            @PathVariable("multiChatRoomId") Long multiChatRoomId){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(multiChatRoomQueryProcessor.getMultiChatRoom(multiChatRoomId));
    }
    @ApiOperation(value = "참여중인 멀티룸들 조회", notes = "U.")
    @GetMapping(value = "/multiChatRooms")
    public ResponseEntity getMultiChatRooms(
            @RequestParam("channelId") Long channelId,
            @ApiIgnore @UserAvatar Avatar avatar){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(multiChatRoomQueryProcessor.getMultiChatRoomsByAvatar(channelId,avatar));
    }

}
