package com.wowtown.wowtownbackend.avatar.controller;

import com.wowtown.wowtownbackend.avatar.application.FriendCommandExecutor;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;


@Validated
@Controller
@RequiredArgsConstructor
public class FriendCommandController {
    FriendCommandExecutor friendCommandExecutor;


    @PostMapping(value ="/friends/newFriend")
    public ResponseEntity FriendRequest(

            @PathVariable Avatar follower,
            @ApiIgnore  @UserAvatar Avatar following){


        friendCommandExecutor.friendRequest(following,follower);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
