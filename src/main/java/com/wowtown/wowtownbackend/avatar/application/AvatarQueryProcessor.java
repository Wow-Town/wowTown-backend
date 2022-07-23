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

@Service
@RequiredArgsConstructor
public class AvatarQueryProcessor {

  // private final StudyGroupQueryProcessor studyGroupQueryProcessor;
  private final AvatarRepository avatarRepository;
  private final AvatarMapper avatarMapper;

  public GetAvatarDto getAvatar(Channel channel, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channel.getId(), user.getId())
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    return avatarMapper.toGetAvatarDto(findAvatar);
  }

  //  public List<GetAvatarDto> getAvatarWithStudyGroupId(Long studyGroupId) {
  //    return studyGroupQueryProcessor.getAvatarWithStudyGroup(studyGroupId).stream()
  //        .map(avatarStudyGroup -> avatarMapper.toGetAvatarDto(avatarStudyGroup.getAvatar()))
  //        .collect(Collectors.toList());
  //  }
}
