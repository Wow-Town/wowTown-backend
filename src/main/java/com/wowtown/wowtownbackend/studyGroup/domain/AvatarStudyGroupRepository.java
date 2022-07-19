package com.wowtown.wowtownbackend.studyGroup.domain;

import java.util.Optional;
import java.util.Set;

public interface AvatarStudyGroupRepository {
    Optional<AvatarStudyGroup> findByAvatarIdAndStudyGroupIdHost(Long avatarId, Long StudyGroupId);
    Optional<AvatarStudyGroup> findByAvatarIdAndStudyGroupId(Long avatarId,Long StudyGroupId);
}
