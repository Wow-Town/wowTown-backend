package com.wowtown.wowtownbackend.avatar.application.common;

import com.wowtown.wowtownbackend.avatar.application.dto.request.CreateOrUpdateAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarFriendDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarFriend;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.user.domain.User;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AvatarMapper {
  default Avatar toAvatar(CreateOrUpdateAvatarDto dto, User user, Channel channel) {
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

  default Avatar toUpdateAvatar(CreateOrUpdateAvatarDto dto) {
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

  default GetAvatarDto toGetAvatarDto(Avatar avatar) {
    if (avatar == null) {
      return null;
    }

    GetAvatarDto getAvatarDto = new GetAvatarDto();
    getAvatarDto.setAvatarId(avatar.getId());
    getAvatarDto.setNickName(avatar.getNickName());
    getAvatarDto.setDescription(avatar.getDescription());
    Set<String> interestList =
        avatar.getInterestSet().stream()
            .map(interest -> interest.getType().toString())
            .collect(Collectors.toSet());
    getAvatarDto.setInterests(interestList);

    return getAvatarDto;
  }

  default GetAvatarFriendDto toGetAvatarFriendDto(AvatarFriend avatarFriend) {
    if (avatarFriend == null) {
      return null;
    }

    GetAvatarFriendDto getAvatarFriendDto = new GetAvatarFriendDto();
    getAvatarFriendDto.setFriendId(avatarFriend.getFriend().getId());
    getAvatarFriendDto.setFriendNickName(avatarFriend.getFriend().getNickName());
    getAvatarFriendDto.setStatus(avatarFriend.getAvatarFriendStatus().toString());

    return getAvatarFriendDto;
  }
}
