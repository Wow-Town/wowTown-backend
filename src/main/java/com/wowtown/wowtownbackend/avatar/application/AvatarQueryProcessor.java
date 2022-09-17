package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarFriendDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetMyAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetOtherAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarFriendStatus;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvatarQueryProcessor {

  private final AvatarRepository avatarRepository;
  private final AvatarMapper avatarMapper;

  public GetMyAvatarDto getMyAvatar(Channel channel, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channel.getId(), user.getId())
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    return avatarMapper.toGetMyAvatarDto(findAvatar);
  }

  public GetOtherAvatarDto getOtherAvatar(long avatarId, Avatar avatar) {
    Avatar findAvatar =
        avatarRepository
            .findById(avatarId)
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    AvatarFriendStatus friendStatus = avatar.checkFriendInAvatarFriendSet(findAvatar);
    return avatarMapper.toGetOtherAvatarDto(findAvatar, friendStatus);
  }

  /// 친구 관련
  public Set<GetAvatarFriendDto> getAvatarFriendList(Avatar avatar) {
    Set<GetAvatarFriendDto> avatarFriendDtoList =
        avatar.getAvatarFriendSet().stream()
            .map(avatarFriend -> avatarMapper.toGetAvatarFriendDto(avatarFriend))
            .collect(Collectors.toCollection(LinkedHashSet::new));

    return avatarFriendDtoList;
  }
}
