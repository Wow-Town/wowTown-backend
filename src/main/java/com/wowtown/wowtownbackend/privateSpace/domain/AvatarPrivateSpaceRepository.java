package com.wowtown.wowtownbackend.privateSpace.domain;

import java.util.List;
import java.util.Optional;

public interface AvatarPrivateSpaceRepository {
  Optional<AvatarPrivateSpace> findAvatarPrivateSpaceWithSessionId(String sessionId);

  List<AvatarPrivateSpace> findAvatarPrivateSpaceByAvatarId(Long avatarId);
}
