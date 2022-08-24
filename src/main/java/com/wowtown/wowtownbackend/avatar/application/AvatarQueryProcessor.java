package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

  /// 친구 관련
  public List<GetAvatarDto> getFriendAvatarDto(List<Avatar> avatarList) {
    List<GetAvatarDto> avatarDtoList = new ArrayList<>();
    for (Avatar findAvatar : avatarList) {

      avatarDtoList.add(avatarMapper.toGetAvatarDto(findAvatar));
    }
    return avatarDtoList;
  }
}
