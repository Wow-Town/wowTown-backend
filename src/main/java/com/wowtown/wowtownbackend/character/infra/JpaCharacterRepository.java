package com.wowtown.wowtownbackend.character.infra;

import com.wowtown.wowtownbackend.character.domain.Character;
import com.wowtown.wowtownbackend.character.domain.CharacterRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCharacterRepository
    extends JpaRepository<Character, Long>, CharacterRepository {
  @Query(
      "select char from Character as char where char.channel.id =:channelId and char.nickName =:nickName")
  Optional<Character> findCharacterWithChannelIdAndNickName(
      @Param("channelId") Long channelId, @Param("nickName") String nickName);

  @Query(
      "select char from Character as char where char.channel.id =:channelId and char.user.id =:userId")
  Optional<Character> findCharacterWithChannelIdAndUserId(
      @Param("channelId") Long channelId, @Param("userId") Long userId);
}
