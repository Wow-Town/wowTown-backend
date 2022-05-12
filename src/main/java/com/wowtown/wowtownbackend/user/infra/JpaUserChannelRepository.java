package com.wowtown.wowtownbackend.user.infra;

import com.wowtown.wowtownbackend.user.domain.UserChannel;
import com.wowtown.wowtownbackend.user.domain.UserChannelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaUserChannelRepository
    extends JpaRepository<UserChannel, Long>, UserChannelRepository {
  @Query(
      "select u_ch from UserChannel as u_ch join fetch User u on u_ch.id = u.id where u_ch.user.id =:userId")
  List<UserChannel> findUserChannelByUserId(@Param("userId") Long userId);
}
