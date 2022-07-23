package com.wowtown.wowtownbackend.chatroom.domain;

import java.util.List;
import java.util.Optional;

public interface MultiChatRoomRepository {
  MultiChatRoom save(MultiChatRoom multiChatRoom);

  void delete(MultiChatRoom multiChatRoom);

  List<MultiChatRoom> findAll();
  //    void deleteALL(); //MultiChatRoom은 한 스터디그룹당 한 개이기떄문에 불필요
  Optional<MultiChatRoom> findById(Long multiChatRoomId);

  List<MultiChatRoom> findByChannelIdAndAvatarId(Long channelId, Long AvatarId);
}
