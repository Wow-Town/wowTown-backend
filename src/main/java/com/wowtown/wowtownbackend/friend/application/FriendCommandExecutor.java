package com.wowtown.wowtownbackend.friend.application;


import com.wowtown.wowtownbackend.avatar.application.AvatarQueryProcessor;
import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.friend.application.common.FriendMapper;
import com.wowtown.wowtownbackend.friend.application.dto.request.FollowAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.friend.domain.Friend;
import com.wowtown.wowtownbackend.friend.domain.FriendRepository;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendCommandExecutor {

    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final AvatarProvider avatarProvider;
    @Transactional
    public long friendRequest(FollowAvatarDto dto, Avatar requesterAvatar){

        Optional<Friend> findFriend =
                friendRepository
                        .checkFriendWithFollowingAndFollowerId(dto.getFollowAvatarId(), requesterAvatar.getId());
        if(!findFriend.isPresent()){ //친구,친구신청 기록이 없을 경우
            Avatar accepterAvatar = avatarProvider.getAvatar(dto.getFollowAvatarId());

            Friend friend = friendRepository.save(friendMapper.toFriend(requesterAvatar, accepterAvatar));
            return friend.getId();
        }
        else{
            if(findFriend.get().checkFriendStatusIsApproved()){
                throw new IllegalStateException("이미 친구입니다");
            }
            else{
                if(findFriend.get().getFollower().getId().equals(requesterAvatar.getId())){
                    throw new IllegalStateException("상대가 이미 친구 신청을 하였습니다");
                }
                else{
                    throw new IllegalStateException("이미 상대에게 친구 신청을 하였습니다");
                }
            }

        }

    }
    @Transactional
    public boolean approveFollowRequest(long friendId){  // 친구 신청,삭제 목록은 해당 아바타만 볼 수 있긴 하지만 검증로직 추가?
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
