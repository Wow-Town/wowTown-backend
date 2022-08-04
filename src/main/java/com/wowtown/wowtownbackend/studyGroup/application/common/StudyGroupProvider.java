package com.wowtown.wowtownbackend.studyGroup.application.common;

import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;

public interface StudyGroupProvider {
  public StudyGroup getStudyGroup(long studyGroupId);
}
