package com.wowtown.wowtownbackend.chatroom.infra;

import com.wowtown.wowtownbackend.chatroom.domain.AvatarChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.AvatarChatRoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaAvatarChatRoomRepository
    extends JpaRepository<AvatarChatRoom, Long>, AvatarChatRoomRepository {
  @Query("select ac from AvatarChatRoom as ac where ac.sessionId =:sessionId")
  Optional<AvatarChatRoom> findAvatarChatRoomWithSessionId(@Param("sessionId") String sessionId);

  @Query("select ac from AvatarChatRoom as ac where ac.avatar.id =:avatarId and ac.active = true")
  List<AvatarChatRoom> findAvatarChatRoomByAvatarId(@Param("avatarId") Long avatarId);
}
