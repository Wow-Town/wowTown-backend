package com.wowtown.wowtownbackend.avatar.controller;


import com.wowtown.wowtownbackend.avatar.application.FriendQueryProcessor;
import com.wowtown.wowtownbackend.avatar.application.common.FriendMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetFriendDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.Friend;
import com.wowtown.wowtownbackend.avatar.domain.FriendRepository;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import lombok.RequiredArgsConstructor;
import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping("/avatars")
public class FriendQueryController {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final FriendQueryProcessor friendQueryProcessor;
    @GetMapping(value = "/friends/friend")
    public ResponseEntity getFriend
            (
               @ApiIgnore @UserAvatar Avatar avatar
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(friendQueryProcessor.getFriend(avatar));
    }

    @GetMapping(value = "/friends/follower")
    public ResponseEntity getFollower
            (
                    @ApiIgnore @UserAvatar Avatar avatar
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(friendQueryProcessor.getFollower(avatar));
    }
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
