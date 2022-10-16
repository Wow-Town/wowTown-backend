package com.wowtown.wowtownbackend.privateSpace.infra;

import com.wowtown.wowtownbackend.privateSpace.domain.PrivateSpace;
import com.wowtown.wowtownbackend.privateSpace.domain.PrivateSpaceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPrivateSpaceRepository
    extends JpaRepository<PrivateSpace, Long>, PrivateSpaceRepository {
  @Query(
      "select ps, aps.privateSpace from PrivateSpace as ps join AvatarPrivateSpace as aps where aps.avatar.id =:avatarId")
  List<PrivateSpace> findPrivateSpaceByAvatarId(@Param("avatarId") Long avatarId);
}
