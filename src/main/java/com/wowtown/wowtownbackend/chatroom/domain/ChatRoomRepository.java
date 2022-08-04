package com.wowtown.wowtownbackend.chatroom.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository {
  ChatRoom save(ChatRoom chatRoom);

  void delete(ChatRoom chatRoom);

  List<ChatRoom> findAll();
  //    void deleteALL(); //MultiChatRoom은 한 스터디그룹당 한 개이기떄문에 불필요

  Optional<ChatRoom> findChatRoomByUuid(UUID chatRoomUuid);

  List<ChatRoom> findChatRoomByAvatarId(Long AvatarId);
}
