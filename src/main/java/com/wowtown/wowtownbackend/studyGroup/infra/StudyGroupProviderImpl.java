package com.wowtown.wowtownbackend.studyGroup.infra;

import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.studyGroup.application.common.StudyGroupProvider;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudyGroupProviderImpl implements StudyGroupProvider {
  private final StudyGroupRepository studyGroupRepository;

  @Override
  public StudyGroup getStudyGroup(long studyGroupId) {
    StudyGroup findStudyGroup =
        studyGroupRepository
            .findById(studyGroupId)
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    return findStudyGroup;
  }
}
