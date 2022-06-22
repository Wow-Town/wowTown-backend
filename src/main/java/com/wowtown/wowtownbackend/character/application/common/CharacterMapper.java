package com.wowtown.wowtownbackend.character.application.common;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.character.application.dto.request.CreateOrUpdateCharacterDto;
import com.wowtown.wowtownbackend.character.application.dto.response.GetCharacterDto;
import com.wowtown.wowtownbackend.character.domain.Character;
import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacterMapper {
  default Character toCharacter(CreateOrUpdateCharacterDto dto, User user, Channel channel) {
    if (dto.getNickName() == null
        && dto.getDescription() == null
        && dto.getInterestList() == null) {
      return null;
    }

    Character character = new Character(dto.getNickName(), dto.getDescription(), user, channel);
    for (String getInterest : dto.getInterestList()) {
      Interest interest = new Interest(InterestType.valueOf(getInterest));
      character.addInterest(interest);
    }

    return character;
  }

  default Character toUpdateCharacter(CreateOrUpdateCharacterDto dto) {
    if (dto.getNickName() == null
        && dto.getDescription() == null
        && dto.getInterestList() == null) {
      return null;
    }

    Character character = new Character(dto.getNickName(), dto.getDescription());
    for (String getInterest : dto.getInterestList()) {
      Interest interest = new Interest(InterestType.valueOf(getInterest));
      character.addInterest(interest);
    }

    return character;
  }

  @Mapping(source = "id", target = "characterId")
  GetCharacterDto toGetCharacterDto(Character character);
}
