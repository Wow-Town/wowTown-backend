package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.FriendMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetFriendDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.Friend;
import com.wowtown.wowtownbackend.avatar.domain.FriendRepository;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendQueryProcessor {


    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;

    public List<GetFriendDto> getFriend(Avatar avatar) {
        List<Friend> findFriendList =
                friendRepository
                        .findFriend(avatar.getId())
                        .stream()
                        .filter(f -> f.checkFriendStatusIsApproved())
                        .collect(Collectors.toList());
        if(findFriendList.isEmpty()){
            throw new InstanceNotFoundException("친구가 없습니다.");
        }

        return friendMapper.toGetFriendDto(findFriendList);
    }

    public List<GetFriendDto> getFollowing( Avatar avatar){
        List<Friend> findFollowingList =
                friendRepository
                        .findWithFollowingId(avatar.getId())
                        .stream()
                        .filter(f -> !f.checkFriendStatusIsApproved())
                        .collect(Collectors.toList());
        if(findFollowingList.isEmpty()){
            throw new InstanceNotFoundException("팔로잉하고 있는 상대가 없습니다.");
        }

        return friendMapper.toGetFriendDto(findFollowingList);
    }
    public List<GetFriendDto> getFollower( Avatar avatar){
        List<Friend> findFriendList =
                friendRepository
                        .findWithFollowerId(avatar.getId())
                        .stream()
                        .filter(f -> f.checkFriendStatusIsApproved())
                        .collect(Collectors.toList());
        if(findFriendList.isEmpty()){
            throw new InstanceNotFoundException("친구가 없습니다.");
        }

        return friendMapper.toGetFriendDto(findFriendList);
    }
}
