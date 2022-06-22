package com.wowtown.wowtownbackend.character.application;

import com.wowtown.wowtownbackend.character.application.common.CharacterMapper;
import com.wowtown.wowtownbackend.character.application.dto.request.CharacterNickNameCheckDto;
import com.wowtown.wowtownbackend.character.application.dto.response.GetCharacterDto;
import com.wowtown.wowtownbackend.character.domain.Character;
import com.wowtown.wowtownbackend.character.domain.CharacterRepository;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacterQueryProcessor {
  private final CharacterRepository characterRepository;
  private final CharacterMapper characterMapper;

  public GetCharacterDto getCharacter(Long channelId, User user) {
    Character findCharacter =
        characterRepository
            .findCharacterWithChannelIdAndUserId(channelId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("캐릭터가 존재하지 않습니다."));
    return characterMapper.toGetCharacterDto(findCharacter);
  }

  public boolean checkCharacterNickNameOverlap(Long channelId, CharacterNickNameCheckDto dto) {
    Optional<Character> findCharacter =
        characterRepository.findCharacterWithChannelIdAndNickName(channelId, dto.getNickName());
    if (findCharacter.isPresent()) {
      throw new IllegalArgumentException("해당 이메일이 이미 존재합니다.");
    }
    return true;
  }
}
