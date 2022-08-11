package com.wowtown.wowtownbackend.friend.application.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetFriendDto {
    private Long FriendId;
    private String FriendNickName;
}
