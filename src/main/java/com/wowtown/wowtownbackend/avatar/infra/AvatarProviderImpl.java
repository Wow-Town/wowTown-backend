package com.wowtown.wowtownbackend.avatar.infra;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AvatarProviderImpl implements AvatarProvider {
  private final AvatarRepository avatarRepository;

  @Override
  public Avatar getAvatar(long avatarId) {
    Avatar findAvatar =
        avatarRepository
            .findById(avatarId)
            .orElseThrow(() -> new IllegalArgumentException("아바타가 존재하지 않습니다."));
    return findAvatar;
  }
}
