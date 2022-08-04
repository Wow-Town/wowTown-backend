package com.wowtown.wowtownbackend.avatar.infra;

import com.wowtown.wowtownbackend.avatar.domain.AvatarChatRoom;
import com.wowtown.wowtownbackend.avatar.domain.AvatarChatRoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAvatarChatRoomRepository
    extends JpaRepository<AvatarChatRoom, Long>, AvatarChatRoomRepository {
  @Query("select ac from AvatarChatRoom as ac where ac.avatar.id =:avatarId")
  List<AvatarChatRoom> findChatRoomAvatarByAvatarId(@Param("avatarId") Long avatarId);
}
