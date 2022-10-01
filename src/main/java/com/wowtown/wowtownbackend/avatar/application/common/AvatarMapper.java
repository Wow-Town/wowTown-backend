package com.wowtown.wowtownbackend.avatar.application.common;

import com.wowtown.wowtownbackend.avatar.application.dto.request.CreateOrUpdateAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarFriendDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetMyAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetOtherAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarFriend;
import com.wowtown.wowtownbackend.avatar.domain.AvatarFriendStatus;
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
        && dto.getInterestList() == null
        && dto.getCostumeIdx() == null) {
      return null;
    }

    Avatar avatar =
        new Avatar(dto.getNickName(), dto.getDescription(), dto.getCostumeIdx(), user, channel);
    for (String getInterest : dto.getInterestList()) {
      Interest interest = new Interest(InterestType.valueOf(getInterest));
      avatar.addInterest(interest);
    }

    return avatar;
  }

  default Avatar toUpdateAvatar(CreateOrUpdateAvatarDto dto) {
    if (dto.getNickName() == null
        && dto.getDescription() == null
        && dto.getInterestList() == null
        && dto.getCostumeIdx() == null) {
      return null;
    }

    Avatar avatar = new Avatar(dto.getNickName(), dto.getDescription(), dto.getCostumeIdx());
    for (String getInterest : dto.getInterestList()) {
      Interest interest = new Interest(InterestType.valueOf(getInterest));
      avatar.addInterest(interest);
    }

    return avatar;
  }

  default GetMyAvatarDto toGetMyAvatarDto(Avatar avatar) {
    if (avatar == null) {
      return null;
    }

    GetMyAvatarDto getMyAvatarDto = new GetMyAvatarDto();
    getMyAvatarDto.setAvatarId(avatar.getId());
    getMyAvatarDto.setNickName(avatar.getNickName());
    getMyAvatarDto.setDescription(avatar.getDescription());
    getMyAvatarDto.setCostumeIdx(avatar.getCostumeIdx());
    Set<String> interestList =
        avatar.getInterestSet().stream()
            .map(interest -> interest.getType().toString())
            .collect(Collectors.toSet());
    getMyAvatarDto.setInterests(interestList);

    return getMyAvatarDto;
  }

  default GetOtherAvatarDto toGetOtherAvatarDto(Avatar avatar, AvatarFriendStatus status) {
    if (avatar == null) {
      return null;
    }

    GetOtherAvatarDto getOtherAvatarDto = new GetOtherAvatarDto();
    getOtherAvatarDto.setAvatarId(avatar.getId());
    getOtherAvatarDto.setNickName(avatar.getNickName());
    getOtherAvatarDto.setDescription(avatar.getDescription());
    Set<String> interestList =
        avatar.getInterestSet().stream()
            .map(interest -> interest.getType().toString())
            .collect(Collectors.toSet());
    getOtherAvatarDto.setInterests(interestList);
    getOtherAvatarDto.setFriendStatus(status.toString());

    return getOtherAvatarDto;
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
