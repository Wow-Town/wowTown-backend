package com.wowtown.wowtownbackend.avatar.domain;

import java.util.List;

public interface AvatarChatRoomRepository {
  List<AvatarChatRoom> findChatRoomAvatarByAvatarId(Long avatarId);
}
