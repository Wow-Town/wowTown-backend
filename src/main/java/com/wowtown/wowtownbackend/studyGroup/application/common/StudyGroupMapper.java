package com.wowtown.wowtownbackend.studyGroup.application.common;

import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDtoRes;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;


@Mapper
public interface StudyGroupMapper {
  default StudyGroup toStudyGroup(String studyGroupName, Integer personnel, String studyDetail) {
    if (studyGroupName == null) { // 제목이 없다면
      return null;
    }
    if (personnel == null) {
      personnel = 1;
    }

    StudyGroup studyGroup = new StudyGroup(studyGroupName, personnel, studyDetail, 1);

    return studyGroup;
  }
  default StudyGroup toStudyGroup(String studyGroupName,Integer personnel, String studyDetail,int isOpen){
    if(studyGroupName ==null){
      return null;

    }
    StudyGroup studyGroup = new StudyGroup(studyGroupName,personnel,studyDetail,isOpen);
    return studyGroup;
  }
  @Mapping(source = "id", target = "studyGroupId")
  GetStudyGroupDtoRes toGetStudyGroupDtoRes(StudyGroup studyGroup);

}
