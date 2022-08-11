package com.wowtown.wowtownbackend.friend.controller;


import com.wowtown.wowtownbackend.friend.application.FriendQueryProcessor;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;


@Validated
@Controller
@RequiredArgsConstructor
public class FriendQueryController {
    private final FriendQueryProcessor friendQueryProcessor;

    @ApiOperation(value = "친구 목록 가져오기", notes = "")
    @GetMapping(value = "/friends")
    public ResponseEntity getFriend
            (
               @ApiIgnore @UserAvatar Avatar avatar
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(friendQueryProcessor.getFriend(avatar));
    }
    @ApiOperation(value = "팔로워 목록 가져오기", notes = "")
    @GetMapping(value = "/friends/follower")
    public ResponseEntity getFollower
            (
                    @ApiIgnore @UserAvatar Avatar avatar
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(friendQueryProcessor.getFollower(avatar));
    }

    @ApiOperation(value = "팔로잉 목록 가져오기", notes = "")
    @GetMapping(value = "/friends/following")
    public ResponseEntity getFollowing
            (
                    @ApiIgnore @UserAvatar Avatar avatar
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(friendQueryProcessor.getFollowing(avatar));
    }
}
