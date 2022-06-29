package com.wowtown.wowtownbackend.studyGroup.application.common;

import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDto;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudyGroupMapper {
  StudyGroup toStudyGroup(
      String subject, int personnel, String description, StudyGroupStatus status);

  @Mapping(source = "id", target = "studyGroupId")
  GetStudyGroupDto toGetStudyGroupDto(StudyGroup studyGroup);
}
