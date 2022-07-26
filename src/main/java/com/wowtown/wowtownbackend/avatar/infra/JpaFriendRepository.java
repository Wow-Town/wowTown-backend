package com.wowtown.wowtownbackend.avatar.infra;

import com.wowtown.wowtownbackend.avatar.domain.Friend;
import com.wowtown.wowtownbackend.avatar.domain.FriendRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaFriendRepository extends JpaRepository<Friend,Long>, FriendRepository {

    @Query("select f from Friend f where f.following.id =: followingId and f.follower.id =: followerId")
     Optional<Friend> findFriendWithFollowingIdAndFollowerId(
             @Param("followingId")Long followingId,
             @Param("followerId")Long followerId );

    List<Friend> findWithFollowingId(Long AvatarId );

    List<Friend> findWithFollowerId(Long AvatarId );
}
