package com.wowtown.wowtownbackend.avatar.controller;

import com.wowtown.wowtownbackend.avatar.application.FriendCommandExecutor;
import com.wowtown.wowtownbackend.avatar.application.dto.request.FollowAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;


@Validated
@Controller
@RequiredArgsConstructor
public class FriendCommandController {
    FriendCommandExecutor friendCommandExecutor;


    @PostMapping(value ="/friends")
    public ResponseEntity friendFollow(
            @RequestBody FollowAvatarDto dto,
            @ApiIgnore  @UserAvatar Avatar following){
        friendCommandExecutor.friendRequest(dto,following);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
    //친구 받아주기
    @PostMapping(value ="/friends/{friendId}")
    public ResponseEntity followApprove(
            @PathVariable("friendId") long friendId){
        friendCommandExecutor.followApprove(friendId);

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
