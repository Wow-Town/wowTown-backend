package com.wowtown.wowtownbackend.studyGroup.infra;

import com.wowtown.wowtownbackend.studyGroup.domain.AvatarStudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.AvatarStudyGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAvatarStudyGroupRepository
    extends JpaRepository<AvatarStudyGroup, Long>, AvatarStudyGroupRepository {
  @Query(
      "select asg from AvatarStudyGroup as asg left join StudyGroup as sg on asg.studyGroup.id = sg.id where asg.avatar.id =:avatarId")
  List<AvatarStudyGroup> findAvatarStudyGroupByAvatarId(@Param("avatarId") Long avatarId);

  @Query(
      "select asg from AvatarStudyGroup as asg left join Avatar as a on asg.avatar.id = a.id where asg.studyGroup.id =:studyGroupId")
  List<AvatarStudyGroup> findAvatarStudyGroupByStudyGroupId(
      @Param("studyGroupId") Long studyGroupId);
}
