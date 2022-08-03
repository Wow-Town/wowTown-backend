package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.common.FriendMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetFriendDto;
import com.wowtown.wowtownbackend.avatar.domain.*;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendQueryProcessor {


    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final AvatarRepository avatarRepository;
    private final AvatarMapper avatarMapper;

    public List<GetAvatarDto> getFriend(Avatar avatar) {
        List<Friend> findFriendList =
                friendRepository
                        .findFriendWithId(avatar.getId())
                        .stream()
                        .filter(f -> f.checkFriendStatusIsApproved())
                        .collect(Collectors.toList()); //여기서 필터로 바로 친구 Avatar로 변경하는 법은 없나?
        if(findFriendList ==null){
            throw new InstanceNotFoundException("친구가 없습니다.");
        }
        List<Avatar> findFriendAvatarList = new ArrayList<>();
        for(Friend friend :findFriendList){
            Optional<Avatar> friendAvatar = avatarRepository.findById(friend.reFriendId(avatar.getId()));
            findFriendAvatarList.add(friendAvatar.get());
        }

        List<GetAvatarDto> AvatarDtoList =
                findFriendAvatarList
                        .stream()
                        .map(avatar1 -> avatarMapper.toGetAvatarDto(avatar1))
                        .collect(Collectors.toList());
        return AvatarDtoList;
    }

    public List<GetAvatarDto> getFollowing( Avatar avatar){
        List<Friend> findFollowingList =
                friendRepository
                        .findWithFollowingId(avatar.getId())
                        .stream()
                        .filter(f -> !f.checkFriendStatusIsApproved())
                        .collect(Collectors.toList());
        if(findFollowingList==null){
            throw new InstanceNotFoundException("팔로잉하고 있는 상대가 없습니다.");
        }
        List<Avatar> findFollowingAvatarList = new ArrayList<>();
        for(Friend friend :findFollowingList){
            Optional<Avatar> followingAvatar = avatarRepository.findById(friend.reFriendId(avatar.getId()));
            findFollowingAvatarList.add(followingAvatar.get());
        }
        List<GetAvatarDto> AvatarDtoList =
                findFollowingAvatarList
                        .stream()
                        .map(avatar1 -> avatarMapper.toGetAvatarDto(avatar1))
                        .collect(Collectors.toList());
        return AvatarDtoList;
    }
    public List<GetAvatarDto> getFollower( Avatar avatar){
        List<Friend> findFollowerList =
                friendRepository
                        .findWithFollowerId(avatar.getId())
                        .stream()
                        .filter(f -> f.checkFriendStatusIsApproved())
                        .collect(Collectors.toList());
        if(findFollowerList==null){
            throw new InstanceNotFoundException("친구 신청한 상대가없습니다.");
        }
        List<Avatar> findFollowerAvatarList = new ArrayList<>();
        for(Friend friend :findFollowerList){
            Optional<Avatar> followingAvatar = avatarRepository.findById(friend.reFriendId(avatar.getId()));
            findFollowerAvatarList.add(followingAvatar.get());
        }
        List<GetAvatarDto> AvatarDtoList =
                findFollowerAvatarList
                        .stream()
                        .map(avatar1 -> avatarMapper.toGetAvatarDto(avatar1))
                        .collect(Collectors.toList());

        return AvatarDtoList;
    }
}
