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
import java.util.Set;

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

  /*public boolean checkAvatarNickNameOverlap(Long channelId, AvatarNickNameCheckDto dto) {
    Optional<Avatar> findAvatar =
        avatarRepository.findAvatarWithChannelIdAndNickName(channelId, dto.getNickName());
    if (findAvatar.isPresent()) {
      throw new IllegalArgumentException("해당 닉네임이 이미 존재합니다.");
    }
    return true;
  }

  public boolean checkAvatarChannelOverlap( Long channelId,User user){
    Set<Avatar> findAvatar =
            avatarRepository.findAvatarWithUserId(user.getId());
    for(Avatar avatar : findAvatar){
      if(channelId == avatar.getChannel().getId()){
        throw new IllegalStateException("해당 채널에 이미 아바타가 존재합니다");
      }
    }
    return true;
  }*/
}
