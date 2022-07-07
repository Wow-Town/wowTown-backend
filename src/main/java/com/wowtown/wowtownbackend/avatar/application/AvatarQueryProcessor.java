package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarQueryProcessor {

  // private final StudyGroupQueryProcessor studyGroupQueryProcessor;
  private final AvatarRepository avatarRepository;
  private final AvatarMapper avatarMapper;

  public GetAvatarDto getAvatar(Long channelId, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channelId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("아바타가 존재하지 않습니다."));
    return avatarMapper.toGetAvatarDto(findAvatar);
  }

  //  public List<GetAvatarDto> getAvatarWithStudyGroupId(Long studyGroupId) {
  //    return studyGroupQueryProcessor.getAvatarWithStudyGroup(studyGroupId).stream()
  //        .map(avatarStudyGroup -> avatarMapper.toGetAvatarDto(avatarStudyGroup.getAvatar()))
  //        .collect(Collectors.toList());
  //  }
}
