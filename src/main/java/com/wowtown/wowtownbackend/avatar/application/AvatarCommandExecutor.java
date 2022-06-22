package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.request.CreateOrUpdateAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvatarCommandExecutor {

  private final AvatarRepository avatarRepository;
  private final ChannelRepository channelRepository;
  private final AvatarMapper avatarMapper;

  @Transactional
  public long createAvatar(Long channelId, CreateOrUpdateAvatarDto dto, User user) {
    Channel findChannel =
        channelRepository
            .findChannelById(channelId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채널입니다."));

    Avatar avatar = avatarRepository.save(avatarMapper.toCharacter(dto, user, findChannel));
    return avatar.getId();
  }

  @Transactional
  public boolean updateAvatar(Long channelId, CreateOrUpdateAvatarDto dto, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channelId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("아바타가 존재하지 않습니다."));
    Avatar updatePayload = avatarMapper.toUpdateCharacter(dto);
    findAvatar.updateCharacter(updatePayload);
    return true;
  }

  @Transactional
  public boolean deleteAvatar(Long channelId, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channelId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("아바타가 존재하지 않습니다."));
    avatarRepository.delete(findAvatar);
    return true;
  }
}
