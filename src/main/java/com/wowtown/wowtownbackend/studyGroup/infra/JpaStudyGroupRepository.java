package com.wowtown.wowtownbackend.studyGroup.infra;

// import com.wowtown.wowtownbackend.common.domain.Interest;
/*import com.wowtown.wowtownbackend.common.domain.InterestType;*/
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
/*import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupInterestTypes;*/
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
  List<StudyGroup> findByStudyGroupNameContaining(String name);

  @Query("select s from StudyGroup s join s.interestTypes i where i =:interest")
  Set<StudyGroup> findByInterestTypes(@Param("interest") InterestType interestType);

  @Query(
      "select s from StudyGroup  s join s.interestTypes i where i in (:interestList)")
  Set<StudyGroup> findByManyInterestTypes(
      @Param("interestList") List<InterestType> interestType);
  // 관심사 1대 다로 해야할 수도?
  // 관심사를 1대 다로 해도...인자로 받는 걸 어떻게 다 확인? ALL로는 실패중
  // 한개만 포함되는 거까지 다 나오는중

  @Query(
      "select distinct s from StudyGroup  s join s.interestTypes i where i not in (:interestList)")
      Set<StudyGroup> findByManyInterestTypes2(
      @Param("interestList") List<InterestType> interestType);
  // create table study_group_interest_types (
  //       study_group_id bigint not null,
  //        interest_types varchar(255)*/

  //이것도 아니면 enum들을 정렬해서 하나의 스트링으로 비교?

}
