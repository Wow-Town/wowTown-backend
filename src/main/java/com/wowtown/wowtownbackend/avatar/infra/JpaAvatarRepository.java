package com.wowtown.wowtownbackend.avatar.infra;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface JpaAvatarRepository extends JpaRepository<Avatar, Long>, AvatarRepository {
  @Query("select av from Avatar as av where av.channel.id =:channelId and av.nickName =:nickName")
  Optional<Avatar> findAvatarWithChannelIdAndNickName(
      @Param("channelId") Long channelId, @Param("nickName") String nickName);

  @Query("select av from Avatar as av where av.channel.id =:channelId and av.user.id =:userId")
  Optional<Avatar> findAvatarWithChannelIdAndUserId(
      @Param("channelId") Long channelId, @Param("userId") Long userId);

  /*@Query("select av from Avatar as av where av.user.id =:userId")
  Set<Avatar> findAvatarWithUserId(
          @Param("userId") Long userId);*/

}
