package com.wowtown.wowtownbackend.avatar.application.common;

import com.wowtown.wowtownbackend.avatar.application.dto.request.CreateOrUpdateAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvatarMapper {
  default Avatar toCharacter(CreateOrUpdateAvatarDto dto, User user, Channel channel) {
    if (dto.getNickName() == null
        && dto.getDescription() == null
        && dto.getInterestList() == null) {
      return null;
    }

    Avatar avatar = new Avatar(dto.getNickName(), dto.getDescription(), user, channel);
    for (String getInterest : dto.getInterestList()) {
      Interest interest = new Interest(InterestType.valueOf(getInterest));
      avatar.addInterest(interest);
    }

    return avatar;
  }

  default Avatar toUpdateCharacter(CreateOrUpdateAvatarDto dto) {
    if (dto.getNickName() == null
        && dto.getDescription() == null
        && dto.getInterestList() == null) {
      return null;
    }

    Avatar avatar = new Avatar(dto.getNickName(), dto.getDescription());
    for (String getInterest : dto.getInterestList()) {
      Interest interest = new Interest(InterestType.valueOf(getInterest));
      avatar.addInterest(interest);
    }

    return avatar;
  }

  @Mapping(source = "id", target = "avatarId")
  GetAvatarDto toGetCharacterDto(Avatar avatar);
}
