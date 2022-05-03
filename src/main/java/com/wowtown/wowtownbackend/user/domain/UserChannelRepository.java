package com.wowtown.wowtownbackend.user.domain;

import java.util.List;

public interface UserChannelRepository {
  List<UserChannel> findUserChannelByUserId(Long userId);
}
