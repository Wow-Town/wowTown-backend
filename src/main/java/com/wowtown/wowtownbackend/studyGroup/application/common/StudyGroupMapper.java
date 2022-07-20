package com.wowtown.wowtownbackend.studyGroup.application.common;

import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDto;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudyGroupMapper {
  default StudyGroup toStudyGroup(
      String subject,
      Integer personnel,
      String description,
      List<String> interestList,
      String status) {
    Set<Interest> interestSet = new HashSet<>();

    if (subject == null
        && personnel == null
        && description == null
        && interestList == null
        && status == null) {
      return null;
    }

    for (String getInterest : interestList) {
      Interest interest = new Interest(InterestType.valueOf(getInterest));
      interestSet.add(interest);
    }

    StudyGroup studyGroup =
        new StudyGroup(
            subject, personnel, description, interestSet, StudyGroupStatus.valueOf(status));

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
    List<String> interestList =
        studyGroup.getInterestSet().stream()
            .map(interest -> interest.getType().toString())
            .collect(Collectors.toList());

    getStudyGroupDto.setInterests(interestList);
    getStudyGroupDto.setStatus(studyGroup.getStatus());

    return getStudyGroupDto;
  }
}
