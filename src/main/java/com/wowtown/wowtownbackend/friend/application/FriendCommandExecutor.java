package com.wowtown.wowtownbackend.friend.application;



import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.friend.application.common.FriendMapper;
import com.wowtown.wowtownbackend.friend.application.dto.request.FollowAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.friend.domain.Friend;
import com.wowtown.wowtownbackend.friend.domain.FriendRepository;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FriendCommandExecutor {

    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final AvatarProvider avatarProvider;

    @Transactional
    public long friendRequest(FollowAvatarDto dto, Avatar requesterAvatar){
        if(requesterAvatar.getId().equals(dto.getFollowAvatarId())){
            throw new IllegalStateException("자신한텐 친구 신청을 할 수 없습니다");
        }
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
    public boolean approveFollowRequest(long friendId){ 
        Friend friend = friendRepository
                .findById(friendId)
                .orElseThrow(()-> new InstanceNotFoundException("해당 친구(신청) 목록이 없습니다"));
        return friend.friendRequestApprove();
    }
    @Transactional
    public boolean deleteFriend(long friendId){ //한번 삭제한 친구에게 다시 친구 신청 가능?
        Friend friend = friendRepository
                .findById(friendId)
                .orElseThrow(() -> new InstanceNotFoundException("해당 친구가 없습니다"));
        friendRepository.delete(friend);
        return true;
    }
    @Transactional
    public boolean followCancel(long friendId){
        return true;
    }
    @Transactional
    public boolean followReject(long friendId){
        return true;
    }
}
