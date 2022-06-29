package com.wowtown.wowtownbackend.studyGroup.domain;

/*import com.wowtown.wowtownbackend.common.domain.InterestType;*/

import com.wowtown.wowtownbackend.common.domain.InterestType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudyGroupRepository {
  StudyGroup save(StudyGroup studyGroup);

  void delete(StudyGroup studyGroup);

  void deleteAll();

  List<StudyGroup> findAll();

  Optional<StudyGroup> findById(Long studyGroupId);

  List<StudyGroup> findBySubjectContaining(String subject);

  Set<StudyGroup> findByInterestTypes(InterestType interestType);

  Set<StudyGroup> findByManyInterestTypes(List<InterestType> interestTypes);

  Set<StudyGroup> findByManyInterestTypes2(List<InterestType> interestTypes);
}
