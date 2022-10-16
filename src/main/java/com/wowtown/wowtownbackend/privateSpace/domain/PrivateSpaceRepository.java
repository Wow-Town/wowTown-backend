package com.wowtown.wowtownbackend.privateSpace.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrivateSpaceRepository {
  PrivateSpace save(PrivateSpace privateSpace);

  void delete(PrivateSpace privateSpace);

  List<PrivateSpace> findAll();
  //    void deleteALL(); //MultiChatRoom은 한 스터디그룹당 한 개이기떄문에 불필요

  Optional<PrivateSpace> findPrivateSpaceByUuid(UUID privateSpaceUuid);

  List<PrivateSpace> findPrivateSpaceByAvatarId(Long AvatarId);
}
