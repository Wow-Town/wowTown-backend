package com.wowtown.wowtownbackend.studyGroup.domain;

import com.wowtown.wowtownbackend.common.domain.InterestType;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyGroupRepository {
    StudyGroup save(StudyGroup studyGroup);
    void delete(StudyGroup studyGroup);
    void deleteAll();
    List<StudyGroup> findAll();
    Optional<StudyGroup> findById(Long studyGroupId);
    List<StudyGroup> findByStudyGroupNameContaining(String name);
    List<StudyGroup> findByInterestTypes(InterestType interestType);
}
