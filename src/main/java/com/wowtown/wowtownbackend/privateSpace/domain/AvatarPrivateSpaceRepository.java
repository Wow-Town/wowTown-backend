package com.wowtown.wowtownbackend.privateSpace.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvatarPrivateSpaceRepository {
  Optional<AvatarPrivateSpace> findAvatarPrivateSpaceWithSessionId(String sessionId);

  List<AvatarPrivateSpace> findAvatarPrivateSpaceByAvatarId(Long avatarId);

  Optional<AvatarPrivateSpace> findAvatarPrivateSpaceByPrivateSpaceUUIDAndAvatarId(
      UUID privateSpaceUUID, Long avatarId);
}
