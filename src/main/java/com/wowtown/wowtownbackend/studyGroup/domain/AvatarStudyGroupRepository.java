package com.wowtown.wowtownbackend.studyGroup.domain;

import java.util.List;

public interface AvatarStudyGroupRepository {
  // 스터디 그룹과 조인하여 아바타가 참여중인 스터디그룹 조회
  List<AvatarStudyGroup> findAvatarStudyGroupByAvatarId(Long avatarId);

  // 아바타와 조인하여 스터디 그룹에 참여중인 아바타 조회
  List<AvatarStudyGroup> findAvatarStudyGroupByStudyGroupId(Long studyGroupId);
}
