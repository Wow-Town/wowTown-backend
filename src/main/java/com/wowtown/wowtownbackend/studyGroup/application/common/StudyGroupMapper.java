package com.wowtown.wowtownbackend.studyGroup.application.common;

import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDto;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudyGroupMapper {
  default StudyGroup toStudyGroup(
      String subject,
      int personnel,
      String description,
      List<InterestType> interests,
      StudyGroupStatus status) {
    List<InterestType> interestTypeList = new ArrayList<>();

    if (subject == null && description == null && status == null) {
      return null;
    }

    String subject1 = null;
    if (subject != null) {
      subject1 = subject;
    }
    String description1 = null;
    if (description != null) {
      description1 = description;
    }
    List<InterestType> interests1 = null;
    if (interests != null) {
      interests1 = interests;
      for (InterestType interestType : interests1) {
        interestTypeList.add(interestType);
      }
    }
    StudyGroupStatus status1 = null;
    if (status != null) {
      status1 = status;
    }
    Integer personnel1 = null;
    personnel1 = personnel;

    StudyGroup studyGroup =
        new StudyGroup(subject1, personnel1, description1, interestTypeList, status1);

    return studyGroup;
  }

  @Mapping(source = "id", target = "studyGroupId")
  default GetStudyGroupDto toGetStudyGroupDto(StudyGroup studyGroup) {
    if (studyGroup == null) {
      return null;
    }

    GetStudyGroupDto getStudyGroupDto = new GetStudyGroupDto();

    getStudyGroupDto.setStudyGroupId(studyGroup.getId());
    getStudyGroupDto.setSubject(studyGroup.getSubject());
    getStudyGroupDto.setPersonnel(studyGroup.getPersonnel());
    getStudyGroupDto.setDescription(studyGroup.getDescription());
    getStudyGroupDto.setInterests(studyGroup.getInterestTypeList());
    getStudyGroupDto.setStatus(studyGroup.getStatus());

    return getStudyGroupDto;
  }
}
