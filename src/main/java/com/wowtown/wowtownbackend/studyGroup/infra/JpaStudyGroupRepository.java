package com.wowtown.wowtownbackend.studyGroup.infra;

import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JpaStudyGroupRepository
    extends JpaRepository<StudyGroup, Long>, StudyGroupRepository {
  @Query(
      "select s from StudyGroup as s where s.channel.id =:channelId and s.subject like %:subject%")
  List<StudyGroup> findByChannelIdAndSubjectContaining(
      @Param("channelId") Long channelId, @Param("subject") String subject);

  @Query(
      "select distinct s from StudyGroup as s join s.interestSet as i where s.channel.id =:channelId and i.type in :interestSet")
  List<StudyGroup> findByChannelIdAndInterestList(
      @Param("channelId") Long channelId, @Param("interestSet") Set<InterestType> interestSet);

  @Query(
      "select distinct s from StudyGroup as s join s.interestSet as i where s.channel.id =:channelId and s.subject like %:subject% and i.type in :interestSet")
  List<StudyGroup> findByChannelIdAndSubjectContainingAndInterestList(
      @Param("channelId") Long channelId,
      @Param("subject") String subject,
      @Param("interestSet") Set<InterestType> interestSet);
}
