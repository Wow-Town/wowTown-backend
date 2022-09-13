package com.wowtown.wowtownbackend.avatar.domain;

import java.util.List;

public interface AvatarFriendRepository {

  List<AvatarFriend> findAvatarFriendByAvatarId(Long AvatarId);

  void delete(AvatarFriend toDelete);
}
