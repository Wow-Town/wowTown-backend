package com.wowtown.wowtownbackend.avatar.domain;

import java.util.Optional;

public interface AvatarRepository {
  Avatar save(Avatar toSave);

  void delete(Avatar toDelete);

  Optional<Avatar> findAvatarWithChannelIdAndNickName(Long channelId, String nickName);

  Optional<Avatar> findAvatarWithChannelIdAndUserId(Long channelId, Long userId);
}
