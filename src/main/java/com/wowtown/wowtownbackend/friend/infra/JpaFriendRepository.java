package com.wowtown.wowtownbackend.friend.infra;

import com.wowtown.wowtownbackend.friend.domain.Friend;
import com.wowtown.wowtownbackend.friend.domain.FriendRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaFriendRepository extends JpaRepository<Friend,Long>, FriendRepository {

//    @Query("select f from Friend f where f.friendStatus =: APPROVED " +
//            "and " +)
//    Optional<Friend> checkFriendWithFollowingIdAndFollowerId( //이미
//             @Param("followingId")Long followingId,
//             @Param("followerId")Long followerId );
//

    @Query("select f from Friend f where f.follower.id =: followingId or f.following.id =:followingId")
    List<Friend> findFriendWithId(@Param("followingId") Long AvatarId); //친구목록 불러오

    @Query("select f from Friend f where f.following.id = :followingId ")
    List<Friend> findWithFollowingId(@Param("followingId") Long AvatarId);//신청한 목록

    @Query("select f from Friend f where f.follower.id = :followerId ")
    List<Friend> findWithFollowerId(@Param("followerId") Long AvatarId );//신청받은 목록

    @Query("select f from Friend f where (f.follower.id =:followerId and f.following.id =:followingId)" +
            "or (f.follower.id =: followingId and f.following.id =:followerId)")
    Optional <Friend> checkFriendWithFollowingAndFollowerId(
            @Param("followerId") Long accepterId,@Param("followingId") Long requesterId
    );
}
