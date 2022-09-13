package com.wowtown.wowtownbackend.avatar.infra;

import com.wowtown.wowtownbackend.avatar.domain.AvatarFriend;
import com.wowtown.wowtownbackend.avatar.domain.AvatarFriendRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAvatarFriendRepository
    extends JpaRepository<AvatarFriend, Long>, AvatarFriendRepository {

  @Query("select af from AvatarFriend as af where af.avatar.id =: avatarId")
  List<AvatarFriend> findAvatarFriendByAvatarId(@Param("avatar") Long AvatarId); // 친구목록 불러오
}
