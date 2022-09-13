package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarFriendDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvatarQueryProcessor {

  private final AvatarRepository avatarRepository;
  private final AvatarMapper avatarMapper;

  public GetAvatarDto getAvatar(Channel channel, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channel.getId(), user.getId())
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    return avatarMapper.toGetAvatarDto(findAvatar);
  }

  public GetAvatarDto getAvatar(long avatarId) {
    Avatar findAvatar =
        avatarRepository
            .findById(avatarId)
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    return avatarMapper.toGetAvatarDto(findAvatar);
  }

  /// 친구 관련
  public List<GetAvatarFriendDto> getAvatarFriendList(Avatar avatar) {
    List<GetAvatarFriendDto> avatarFriendDtoList =
        avatar.getAvatarFriendList().stream()
            .map(avatarFriend -> avatarMapper.toGetAvatarFriendDto(avatarFriend))
            .collect(Collectors.toList());

    return avatarFriendDtoList;
  }
}
