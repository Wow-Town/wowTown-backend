package com.wowtown.wowtownbackend.friend.application.common;


import com.wowtown.wowtownbackend.friend.application.dto.GetFriendDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.friend.domain.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel ="spring")
public interface FriendMapper {
    default Friend toFriend(Avatar following, Avatar follower){
        Friend friend = new Friend(following,follower);
        return friend;
    }




}
