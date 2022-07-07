package com.wowtown.wowtownbackend.avatar.application.common;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;

public interface AvatarProvider {
  public Avatar getAvatar(long avatarId);
}
