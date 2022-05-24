package com.wowtown.wowtownbackend.studyGroup.infra;

//import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaStudyGroupRepository
    extends JpaRepository<StudyGroup, Long>, StudyGroupRepository {
  List<StudyGroup> findByStudyGroupNameContaining(String name);

  /*@Query("select s from StudyGroup  s where s.studyGroupName like %:studyGroupName%")
  List<StudyGroup> findByStudyGroupName(@Param("studyGroupName") String name);*/

  @Query("select s from StudyGroup  s join s.interestTypes i where i =:interest")
  List<StudyGroup> findByInterestTypes(@Param("interest") InterestType interestType);

  /*@Query("select s from StudyGroup  s join s.interestTypes i where i = all (select a from  a)")
  List<StudyGroup> findByManyInterestTypes(@Param("interestList") List<InterestType> interestTypes);*/
  // 관심사 1대 다로 해야할 수도?
  // create table study_group_interest_types (
  //       study_group_id bigint not null,
  //        interest_types varchar(255)
}
