package com.wowtown.wowtownbackend.friend.controller;

import com.wowtown.wowtownbackend.friend.application.FriendCommandExecutor;
import com.wowtown.wowtownbackend.friend.application.dto.request.FollowAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


@Validated
@Controller
@RequiredArgsConstructor
public class FriendCommandController {
    private final FriendCommandExecutor friendCommandExecutor;


    @ApiOperation(value = "친구 신청", notes = "")
    @PostMapping(value ="/friends")
    public ResponseEntity follow(
            @Valid @RequestBody FollowAvatarDto dto,
            @ApiIgnore  @UserAvatar Avatar following){
        System.out.println("follow받는 아바타의 아이디는"+dto.getFollowerAvatarId());
        friendCommandExecutor.friendRequest(dto,following);


        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
    //친구 받아주기
    @PostMapping(value ="/friends/{friendId}")
    public ResponseEntity approveFollow(
            @PathVariable("friendId") long friendId){
        friendCommandExecutor.approveFollow(friendId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    //친구 삭제하기
    @DeleteMapping(value ="/friends/{friendId}")
    public ResponseEntity deleteFriend(
            @PathVariable("friendId") long friendId){

        friendCommandExecutor.deleteFriend(friendId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
