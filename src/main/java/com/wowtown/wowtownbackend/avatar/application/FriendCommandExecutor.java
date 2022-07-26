package com.wowtown.wowtownbackend.avatar.application;


import com.wowtown.wowtownbackend.avatar.application.common.FriendMapper;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.avatar.domain.Friend;
import com.wowtown.wowtownbackend.avatar.domain.FriendRepository;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendCommandExecutor {

    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;

    @Transactional
    public long friendRequest(Avatar followingAvatar,Avatar followerAvatar){
        Optional<Friend> findFriend =
                friendRepository.findFriendWithFollowingIdAndFollowerId(followingAvatar.getId(),followerAvatar.getId());
        if(findFriend.isPresent()){
            if(findFriend.get().checkFriendStatusIsApproved()){
                throw new IllegalStateException("이미 친구인 아바타입니다");
            }
            else{
                throw new IllegalStateException("이미 친구 신청을 한 상태입니다");
            }
        }
        Friend friend = friendRepository.save(friendMapper.toFriend(followingAvatar, followerAvatar));
        return friend.getId();
    }
}
