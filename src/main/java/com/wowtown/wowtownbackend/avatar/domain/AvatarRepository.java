package com.wowtown.wowtownbackend.avatar.domain;

import java.util.Optional;
import java.util.Set;

public interface AvatarRepository {
  Avatar save(Avatar toSave);

  void delete(Avatar toDelete);

  Optional<Avatar> findAvatarWithChannelIdAndNickName(Long channelId, String nickName);

  Optional<Avatar> findAvatarWithChannelIdAndUserId(Long channelId, Long userId);
  Set<Avatar> findAvatarWithUserId(Long userId);
}
