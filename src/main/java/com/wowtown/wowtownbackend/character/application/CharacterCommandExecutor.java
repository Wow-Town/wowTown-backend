package com.wowtown.wowtownbackend.character.application;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import com.wowtown.wowtownbackend.character.application.common.CharacterMapper;
import com.wowtown.wowtownbackend.character.application.dto.request.CreateOrUpdateCharacterDto;
import com.wowtown.wowtownbackend.character.domain.Character;
import com.wowtown.wowtownbackend.character.domain.CharacterRepository;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterCommandExecutor {

  private final CharacterRepository characterRepository;
  private final ChannelRepository channelRepository;
  private final CharacterMapper characterMapper;

  @Transactional
  public long createCharacter(Long channelId, CreateOrUpdateCharacterDto dto, User user) {
    Channel findChannel =
        channelRepository
            .findChannelById(channelId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채널입니다."));

    Character character =
        characterRepository.save(characterMapper.toCharacter(dto, user, findChannel));
    return character.getId();
  }

  @Transactional
  public boolean updateCharacter(Long channelId, CreateOrUpdateCharacterDto dto, User user) {
    Character findCharacter =
        characterRepository
            .findCharacterWithChannelIdAndUserId(channelId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("캐릭터가 존재하지 않습니다."));
    Character updatePayload = characterMapper.toUpdateCharacter(dto);
    findCharacter.updateCharacter(updatePayload);
    return true;
  }

  @Transactional
  public boolean deleteCharacter(Long channelId, User user) {
    Character findCharacter =
        characterRepository
            .findCharacterWithChannelIdAndUserId(channelId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("캐릭터가 존재하지 않습니다."));
    characterRepository.delete(findCharacter);
    return true;
  }
}
