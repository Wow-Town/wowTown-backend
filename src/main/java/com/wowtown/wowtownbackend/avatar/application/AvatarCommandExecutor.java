package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.request.CreateOrUpdateAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvatarCommandExecutor {

  private final AvatarRepository avatarRepository;
  private final AvatarMapper avatarMapper;

  @Transactional
  public long createAvatar(CreateOrUpdateAvatarDto dto, Channel channel, User user) {
    // 아바타에 닉네임이 있는지 존재 확인 ->
    Optional<Avatar> findAvatar =
        avatarRepository.findAvatarWithChannelIdAndUserIdAndNickName(
            channel.getId(), user.getId(), dto.getNickName());
    if (findAvatar.isPresent()) {
      Avatar avatar = findAvatar.get();
      if (avatar.getUser().equals(user)) {
        throw new InstanceNotFoundException("해당 채널에 아바타가 이미 존재합니다.");
      }
      if (avatar.getNickName().equals(dto.getNickName())) {
        throw new InstanceNotFoundException("해당 닉네임이 이미 존재합니다.");
      }
    }
    Avatar avatar = avatarRepository.save(avatarMapper.toAvatar(dto, user, channel));
    return avatar.getId();
  }
  // 채널 아이디, 유저 가지고 디비 조회 있을 경우 아바타존재 아니면 새로만들기
  // dto의 닉네임을 가지고 조회중복이 되면 빠꾸
  @Transactional
  public boolean updateAvatar(CreateOrUpdateAvatarDto dto, Channel channel, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channel.getId(), user.getId())
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    Avatar updatePayload = avatarMapper.toUpdateAvatar(dto);
    findAvatar.updateAvatar(updatePayload);
    return true;
  }

  @Transactional
  public boolean deleteAvatar(Channel channel, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channel.getId(), user.getId())
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    avatarRepository.delete(findAvatar);
    return true;
  }
}
