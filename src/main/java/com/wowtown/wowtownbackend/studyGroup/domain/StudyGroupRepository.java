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

  List<StudyGroup> findByAvatarId(Long avatarId);

  List<StudyGroup> findBySubjectContaining(String subject);

  List<StudyGroup> findByInterestList(Set<InterestType> interestList);

  List<StudyGroup> findBySubjectContainingAndInterestList(
      String subject, Set<InterestType> interestList);
}
