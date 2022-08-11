package com.wowtown.wowtownbackend.friend.application;

import com.wowtown.wowtownbackend.avatar.application.AvatarQueryProcessor;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.friend.domain.FriendRepository;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendQueryProcessor {


    private final FriendRepository friendRepository;
    private final AvatarQueryProcessor avatarQueryProcessor;

    public List<GetAvatarDto> getFriend(Avatar avatar) {
        List<Avatar> findFriendList =
                friendRepository
                        .findFriendWithId(avatar.getId())
                        .stream()
                        .filter(f -> f.checkFriendStatusIsApproved())
                        .map(f->f.reFriendId(avatar.getId()))
                        .collect(Collectors.toList());
        if(findFriendList.isEmpty()){
            throw new InstanceNotFoundException("친구가 없습니다.");
        }
        return avatarQueryProcessor.getFriendAvatarDto(findFriendList);
    }
    public List<GetAvatarDto> getFollowing( Avatar avatar){
        List<Avatar> findFollowingAvatarList =
                friendRepository
                        .findWithFollowingId(avatar.getId())
                        .stream()
                        .filter(f -> !f.checkFriendStatusIsApproved())
                        .map(f->f.reFriendId(avatar.getId()))
                        .collect(Collectors.toList());
        if(findFollowingAvatarList.isEmpty()){
            throw new InstanceNotFoundException("팔로잉이 없습니다.");
        }
        return avatarQueryProcessor.getFriendAvatarDto(findFollowingAvatarList);
    }
    public List<GetAvatarDto> getFollower( Avatar avatar){
        List<Avatar> findFollowerAvatarList =
                friendRepository
                        .findWithFollowerId(avatar.getId())
                        .stream()
                        .filter(f -> !f.checkFriendStatusIsApproved())
                        .map(f->f.reFriendId(avatar.getId()))
                        .collect(Collectors.toList());
        if(findFollowerAvatarList.isEmpty()){
            throw new InstanceNotFoundException("팔로워가 없습니다.");
        }
        return avatarQueryProcessor.getFriendAvatarDto(findFollowerAvatarList);
    }
}
