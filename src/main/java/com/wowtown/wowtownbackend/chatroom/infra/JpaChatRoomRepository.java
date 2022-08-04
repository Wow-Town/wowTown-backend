package com.wowtown.wowtownbackend.chatroom.infra;

import com.wowtown.wowtownbackend.chatroom.domain.ChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepository {

  @Query(
      "select c, ca.chatRoom from ChatRoom as c join AvatarChatRoom as ca where ca.avatar.id =:avatarId")
  List<ChatRoom> findChatRoomByAvatarId(@Param("avatarId") Long avatarId);
}
