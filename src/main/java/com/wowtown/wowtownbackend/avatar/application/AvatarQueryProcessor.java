package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.request.AvatarNickNameCheckDto;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvatarQueryProcessor {
  private final AvatarRepository avatarRepository;
  private final AvatarMapper avatarMapper;

  public GetAvatarDto getAvatar(Long channelId, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channelId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("아바타가 존재하지 않습니다."));
    return avatarMapper.toGetCharacterDto(findAvatar);
  }

  public boolean checkAvatarNickNameOverlap(Long channelId, AvatarNickNameCheckDto dto) {
    Optional<Avatar> findCharacter =
        avatarRepository.findAvatarWithChannelIdAndNickName(channelId, dto.getNickName());
    if (findCharacter.isPresent()) {
      throw new IllegalArgumentException("해당 이메일이 이미 존재합니다.");
    }
    return true;
  }
}
