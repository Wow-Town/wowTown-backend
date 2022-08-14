package com.wowtown.wowtownbackend.avatar.domain;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository {
  Avatar save(Avatar toSave);

  void delete(Avatar toDelete);

  Optional<Avatar> findById(Long avatarId);

  Optional<Avatar> findAvatarWithChannelIdAndUserId(Long channelId, Long userId);

  Optional<Avatar> findAvatarWithChannelIdAndUserIdAndNickName(
      Long channelId, Long userId, String nickName);

  List<Avatar> findAvatarByChatRoomId(Long chatRoomId);
}
