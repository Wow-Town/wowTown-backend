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

  List<StudyGroup> findByChannelId(Long channelId);

  Optional<StudyGroup> findById(Long studyGroupId);

  List<StudyGroup> findByChannelIdAndSubjectContaining(Long channelId, String subject);

  List<StudyGroup> findByChannelIdAndInterestList(Long channelId, Set<InterestType> interestList);

  List<StudyGroup> findByChannelIdAndSubjectContainingAndInterestList(
      Long channelId, String subject, Set<InterestType> interestList);
}
