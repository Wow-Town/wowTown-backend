package com.wowtown.wowtownbackend.avatar.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository {

    List<Friend> findFriendWithId(Long AvatarId);
    List<Friend> findWithFollowingId(Long AvatarId );
    List<Friend> findWithFollowerId(Long AvatarId );
    Friend save(Friend toSave);
    Optional <Friend> findById(long friendId);
    Optional<Friend> checkFriendWithFollowingIdAndFollowerId(Long followingId,Long followerId );
    void delete(Friend toDelete);


    //친구를 끊은 상태라면? following follower 둘 다 삭제하는 걸로



}
