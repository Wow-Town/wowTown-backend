package com.wowtown.wowtownbackend.friend.domain;

import com.wowtown.wowtownbackend.friend.domain.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendRepository {

    List<Friend> findFriendWithId(Long AvatarId);
    List<Friend> findWithFollowingId(Long AvatarId);
    List<Friend> findWithFollowerId(Long AvatarId);
    Friend save(Friend toSave);
    Optional <Friend> findById(long friendId);
    void delete(Friend toDelete);
    Optional <Friend> checkFriendWithFollowingAndFollowerId(Long accepterId,Long requesterId);



}
