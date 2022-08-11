package com.wowtown.wowtownbackend.friend.application.common;


import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.friend.domain.Friend;
import org.mapstruct.Mapper;

@Mapper(componentModel ="spring")
public interface FriendMapper {
    default Friend toFriend(Avatar following, Avatar follower){
        Friend friend = new Friend(following,follower);
        return friend;
    }




}
