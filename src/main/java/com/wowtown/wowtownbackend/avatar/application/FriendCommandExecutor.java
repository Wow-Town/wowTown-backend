package com.wowtown.wowtownbackend.avatar.application;


import com.wowtown.wowtownbackend.avatar.application.common.FriendMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.request.FollowAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.*;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendCommandExecutor {

    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final AvatarRepository avatarRepository;
    @Transactional
    public long friendRequest(FollowAvatarDto dto, Avatar followingAvatar){
        Optional<Avatar> followerAvatar = avatarRepository.findById(dto.getFollowerAvatarId());
        List<Friend> friendList =
                friendRepository
                        .findFriendWithId(followingAvatar.getId())
                        .stream()
                        .collect(Collectors.toList());
        if(friendList == null){
            Friend friend = friendRepository.save(friendMapper.toFriend(followingAvatar, followerAvatar.get()));
            return friend.getId();
        }
        List<Friend> findFriendList =
                friendRepository
                        .findFriendWithId(followingAvatar.getId())
                        .stream()
                        .filter(f->f.checkFriendStatusIsApproved())
                        .collect(Collectors.toList());
        for(Friend friend : findFriendList){
            Optional<Avatar> friendAvatar = avatarRepository.findById(friend.reFriendId(followingAvatar.getId())); // 친구의 Id를 가져옴
            if(followerAvatar.get().equals(friendAvatar)){
                throw new IllegalStateException("이미 친구인 아바타입니다");
            }
        }
        List<Friend> findFollowingList =
                friendRepository
                        .findWithFollowingId(followingAvatar.getId())
                        .stream()
                        .filter(f->!f.checkFriendStatusIsApproved())
                        .collect(Collectors.toList());;
        for(Friend friend : findFollowingList){
            Optional<Avatar> friendAvatar = avatarRepository.findById(friend.reFriendId(followingAvatar.getId())); // 친구의 Id를 가져옴
            if(followerAvatar.get().equals(friendAvatar)){
                throw new IllegalStateException("이미 친구 신청을 한 아바타입니다");
            }
        }
        List<Friend> findFollowerList =
                friendRepository
                        .findWithFollowerId(followingAvatar.getId())
                        .stream()
                        .filter(f->!f.checkFriendStatusIsApproved())
                        .collect(Collectors.toList());;
        for(Friend friend : findFriendList) {
            Optional<Avatar> friendAvatar = avatarRepository.findById(friend.reFriendId(followingAvatar.getId())); // 친구의 Id를 가져옴
            if (followerAvatar.get().equals(friendAvatar)) {
                throw new IllegalStateException("상대가 이미 친구 신청을 하였습니다");
            }
        }
        return followerAvatar.get().getId(); //임시방편
    }
    @Transactional
    public boolean approveFollow(long friendId){  // 친구 신청,삭제 목록은 해당 아바타만 볼 수 있긴 하지만 검증로직 추가?
        Optional<Friend> friend = friendRepository.findById(friendId);
        friend.get().friendRequestApprove();
        return true;
    }

    @Transactional
    public boolean deleteFriend(long friendId){ //한번 삭제한 친구에게 다시 친구 신청 가능?
        Optional<Friend> friend =
                friendRepository
                        .findById(friendId);
        if(friend.isPresent()){
            friendRepository.delete(friend.get());
            return true;

        }
        throw new InstanceNotFoundException("존재하지 않는 친구입니다. ");
    }

}
