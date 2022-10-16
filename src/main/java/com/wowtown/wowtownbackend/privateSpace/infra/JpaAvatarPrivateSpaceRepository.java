package com.wowtown.wowtownbackend.privateSpace.infra;

import com.wowtown.wowtownbackend.privateSpace.domain.AvatarPrivateSpace;
import com.wowtown.wowtownbackend.privateSpace.domain.AvatarPrivateSpaceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaAvatarPrivateSpaceRepository
    extends JpaRepository<AvatarPrivateSpace, Long>, AvatarPrivateSpaceRepository {
  @Query("select aps from AvatarPrivateSpace as aps where aps.sessionId =:sessionId")
  Optional<AvatarPrivateSpace> findAvatarPrivateSpaceWithSessionId(
      @Param("sessionId") String sessionId);

  @Query("select aps from AvatarPrivateSpace as aps where aps.avatar.id =:avatarId")
  List<AvatarPrivateSpace> findAvatarPrivateSpaceByAvatarId(@Param("avatarId") Long avatarId);
}
