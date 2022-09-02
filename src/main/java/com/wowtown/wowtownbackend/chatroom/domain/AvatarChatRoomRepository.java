package com.wowtown.wowtownbackend.chatroom.domain;

import java.util.List;
import java.util.Optional;

public interface AvatarChatRoomRepository {
  Optional<AvatarChatRoom> findAvatarChatRoomWithSessionId(String sessionId);

  List<AvatarChatRoom> findAvatarChatRoomByAvatarId(Long avatarId);
}
