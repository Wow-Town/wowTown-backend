package com.wowtown.wowtownbackend.character.domain;

import java.util.Optional;

public interface CharacterRepository {
  Character save(Character toSave);

  void delete(Character toDelete);

  Optional<Character> findCharacterWithChannelIdAndNickName(Long channelId, String nickName);

  Optional<Character> findCharacterWithChannelIdAndUserId(Long channelId, Long userId);
}
