package com.wowtown.wowtownbackend.avatar.application.common;


import com.wowtown.wowtownbackend.avatar.application.dto.response.GetFriendDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel ="spring")
public interface FriendMapper {
    default Friend toFriend(Avatar following, Avatar follower){
        Friend friend = new Friend(following,follower);
        return friend;
    }

    @Mapping(source ="id" , target ="FriendId")
    List<GetFriendDto> toGetFriendDto(List<Friend> findFriendList);


}
